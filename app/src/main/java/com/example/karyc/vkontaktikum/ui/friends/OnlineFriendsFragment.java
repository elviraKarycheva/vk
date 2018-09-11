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
import com.example.karyc.vkontaktikum.core.Friend;
import com.example.karyc.vkontaktikum.core.RetrofitProvider;
import com.example.karyc.vkontaktikum.core.network.FriendsApi;
import com.example.karyc.vkontaktikum.core.network.responseObjects.CommonResponse;
import com.example.karyc.vkontaktikum.core.network.responseObjects.GetFriendsResponse;
import com.example.karyc.vkontaktikum.core.network.responseObjects.ResponseFriendDelete;
import com.example.karyc.vkontaktikum.ui.MarginItemDecoration;
import com.example.karyc.vkontaktikum.ui.UserProfileActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.example.karyc.vkontaktikum.ui.LoginActivity.SAVED_ACCESS_TOKEN;

public class OnlineFriendsFragment extends android.support.v4.app.Fragment implements FriendsAdapter.FriendsAdapterListener {
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
        return inflater.inflate(R.layout.online_friends_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeRefreshLayout = view.findViewById(R.id.swipe_online_friends);

        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.colorPrimary));

        RecyclerView mRecyclerView = view.findViewById(R.id.recyclerview_online_friends);
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

        FriendsApi friendsApi = RetrofitProvider.getFriendsApi();
        friendsApi
                .getAllFriends(accessToken, "5.80", "photo_200_orig")
                .enqueue(new Callback<CommonResponse<GetFriendsResponse>>() {
                    @Override
                    public void onResponse(@NonNull Call<CommonResponse<GetFriendsResponse>> call, @NonNull Response<CommonResponse<GetFriendsResponse>> response) {
                        if (response.isSuccessful()) {
                            CommonResponse<GetFriendsResponse> body = response.body();
                            Log.d("successful", body.toString());
                            ArrayList<Friend> allFriends = body.response.items;
                            ArrayList<Friend> onlineFriends = new ArrayList<>();

                            for (Friend currentItem : allFriends) {
                                if(currentItem.getOnline() == 1) {
                                    onlineFriends.add(currentItem);
                                }
                            }
                            mAdapter.setFriends(onlineFriends);
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<CommonResponse<GetFriendsResponse>> call, @NonNull Throwable t) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });


    }

    public void onButtonDeleteFriend(final long id) {
        String title = "Отправить в подписчики?";
        String buttonDeleteFriend = "Удалить из друзей";
        String buttonCancel = "Отмена";

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
                .enqueue(new Callback<CommonResponse<ResponseFriendDelete>>() {
                    @Override
                    public void onResponse(Call<CommonResponse<ResponseFriendDelete>> call, Response<CommonResponse<ResponseFriendDelete>> response) {
                        if (response.isSuccessful()) {
                            CommonResponse<ResponseFriendDelete> body = response.body();
                            Log.d("successful", body.toString());
                            loadData();
                        }
                    }

                    @Override
                    public void onFailure(Call<CommonResponse<ResponseFriendDelete>> call, Throwable t) {

                    }
                });
    }

    public void onUserGetInfo(long id) {
        Intent intent = new Intent(getActivity(), UserProfileActivity.class);
        intent.putExtra(ID_USER, id);
        startActivity(intent);
    }
}
