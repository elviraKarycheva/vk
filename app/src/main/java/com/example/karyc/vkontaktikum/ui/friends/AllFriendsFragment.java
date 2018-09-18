package com.example.karyc.vkontaktikum.ui.friends;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.karyc.vkontaktikum.R;
import com.example.karyc.vkontaktikum.core.RetrofitProvider;
import com.example.karyc.vkontaktikum.core.network.FriendsApi;
import com.example.karyc.vkontaktikum.core.network.responseObjects.CommonResponse;
import com.example.karyc.vkontaktikum.core.network.responseObjects.GetFriendsResponse;
import com.example.karyc.vkontaktikum.core.network.responseObjects.ResponseFriendDelete;
import com.example.karyc.vkontaktikum.ui.MarginItemDecoration;
import com.example.karyc.vkontaktikum.ui.UserProfileActivity;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.Context.MODE_PRIVATE;
import static com.example.karyc.vkontaktikum.ui.LoginActivity.SAVED_ACCESS_TOKEN;

public class AllFriendsFragment extends android.support.v4.app.Fragment implements FriendsAdapter.FriendsAdapterListener {
    private FriendsAdapter mAdapter = new FriendsAdapter();
    private String accessToken;
    private SwipeRefreshLayout swipeRefreshLayout;
    public static final String ID_USER = "idUser";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.all_friends_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeRefreshLayout = view.findViewById(R.id.swipe_friends);

        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorPrimary));

        RecyclerView mRecyclerView = view.findViewById(R.id.recyclerview_friends);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        int dimenSide = (int) getResources().getDimension(R.dimen.margin_side);
        int dimenTop = (int) getResources().getDimension(R.dimen.margin_top);
        mRecyclerView.addItemDecoration(new MarginItemDecoration(dimenSide, dimenTop));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        loadData();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }

    private void loadData() {
        swipeRefreshLayout.setRefreshing(true);
        mAdapter.listener = this;
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ACCESS_TOKEN_STORAGE", MODE_PRIVATE);
        accessToken = sharedPreferences.getString(SAVED_ACCESS_TOKEN, null);
        RetrofitProvider retrofitProvider = new RetrofitProvider();

        FriendsApi friendsApi = retrofitProvider.getFriendsApi();
        friendsApi
                .getAllFriends(accessToken, "5.80", "photo_200_orig")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CommonResponse<GetFriendsResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(CommonResponse<GetFriendsResponse> getFriendsResponseCommonResponse) {
                        Log.d("successful", getFriendsResponseCommonResponse.toString());
                        mAdapter.setFriends(getFriendsResponseCommonResponse.response.items);
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        swipeRefreshLayout.setRefreshing(false);

                    }
                });
    }

    public void onButtonDeleteFriend(final long id) {
        String title = getContext().getString(R.string.title_alert_dialog);
        String buttonDeleteFriend = getContext().getString(R.string.button_delete_friend_alert_dialog);
        String buttonCancel = getContext().getString(R.string.cancel_alert_dialog);

        AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
        ad.setTitle(title);
        ad.setPositiveButton(buttonDeleteFriend, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                Toast.makeText(getActivity(), "Вы сделали правильный выбор",
                        Toast.LENGTH_LONG).show();
                onDeleteFriend(id);
            }
        });
        ad.setNegativeButton(buttonCancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                Toast.makeText(getActivity(), "Возможно вы правы", Toast.LENGTH_LONG)
                        .show();
            }
        });

        ad.setCancelable(true);
        ad.show();
    }

    public void onDeleteFriend(long id) {
        FriendsApi friendsApi = RetrofitProvider.getFriendsApi();

        friendsApi
                .getDeleteFriend(accessToken, "5.80", id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CommonResponse<ResponseFriendDelete>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(CommonResponse<ResponseFriendDelete> responseFriendDeleteCommonResponse) {
                        Log.d("successful", responseFriendDeleteCommonResponse.toString());
                        loadData();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void onUserGetInfo(long id) {
        Intent intent = new Intent(getActivity(), UserProfileActivity.class);
        intent.putExtra(ID_USER, id);
        startActivity(intent);
    }
}
