package com.example.karyc.vkontaktikum.ui.friends;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.karyc.vkontaktikum.R;
import com.example.karyc.vkontaktikum.core.Friend;
import com.example.karyc.vkontaktikum.databinding.OnlineFriendsFragmentBinding;
import com.example.karyc.vkontaktikum.ui.MarginItemDecoration;
import com.example.karyc.vkontaktikum.ui.UserProfileActivity;

import java.util.ArrayList;
import java.util.Objects;

public class FriendsFragment extends android.support.v4.app.Fragment implements FriendsAdapter.FriendsAdapterListener {
    private FriendsAdapter adapter = new FriendsAdapter();
    public static final String ID_USER = "idUser";
    OnlineFriendsFragmentBinding binding;
    private FriendsViewModel viewModel;
    boolean showOnline;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(FriendsViewModel.class);
        viewModel.getFriendsLiveData().observe(this, new Observer<ArrayList<Friend>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Friend> friends) {
                if (friends == null) {
                    return;
                }
                adapter.setFriends(friends);
                binding.swipeOnlineFriends.setRefreshing(false);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.online_friends_fragment, container, false);
        View view = binding.getRoot();
        return view;// для создания фрагмента нужна вью
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SwipeRefreshLayout swipeRefreshLayout = binding.swipeOnlineFriends;
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(
                Objects.requireNonNull(getContext()), R.color.colorPrimary));

        binding.recyclerviewOnlineFriends.setHasFixedSize(true);
        binding.recyclerviewOnlineFriends.setAdapter(adapter);
        adapter.listener = this;

        int dimenSide = (int) getResources().getDimension(R.dimen.margin_side);
        int dimenTop = (int) getResources().getDimension(R.dimen.margin_top);
        binding.recyclerviewOnlineFriends.addItemDecoration(new MarginItemDecoration(dimenSide, dimenTop));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        binding.recyclerviewOnlineFriends.setLayoutManager(mLayoutManager);

        viewModel.loadData(showOnline);
        swipeRefreshLayout.setRefreshing(true);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.loadData(showOnline);
            }
        });
    }

    public void onUserGetInfo(long id) {
        Intent intent = new Intent(getActivity(), UserProfileActivity.class);
        intent.putExtra(ID_USER, id);
        startActivity(intent);
    }

    @Override
    public void onButtonDeleteFriend(final long id) {
        if (getActivity() == null) {
            return;
        }
        String title = this.getString(R.string.title_alert_dialog);
        String buttonDeleteFriend = this.getString(R.string.button_delete_friend_alert_dialog);
        String buttonCancel = this.getString(R.string.cancel_alert_dialog);

        AlertDialog.Builder ad = new AlertDialog.Builder(this.getActivity());
        ad.setTitle(title);
        ad.setPositiveButton(buttonDeleteFriend, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                Toast.makeText(getActivity(), "Good",
                        Toast.LENGTH_LONG).show();
                viewModel.onDeleteFriend(id, showOnline);
            }
        });
        ad.setNegativeButton(buttonCancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                Toast.makeText(getActivity(), "You're right", Toast.LENGTH_LONG)
                        .show();
            }
        });
        ad.setCancelable(true);
        ad.show();
    }
}
