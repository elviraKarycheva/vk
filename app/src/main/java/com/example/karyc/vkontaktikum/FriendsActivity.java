package com.example.karyc.vkontaktikum;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.karyc.vkontaktikum.LoginActivity.SAVED_ACCESS_TOKEN;

public class FriendsActivity extends AppCompatActivity {
    private FriendsAdapter mAdapter = new FriendsAdapter();
    private String accessToken;
    private AlertDialog.Builder ad;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        swipeRefreshLayout = findViewById(R.id.swipe_friends);
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
        mAdapter.friendActivity = this;
        SharedPreferences sharedPreferences = getSharedPreferences("ACCESS_TOKEN_STORAGE", MODE_PRIVATE);
        accessToken = sharedPreferences.getString(SAVED_ACCESS_TOKEN, null);
        RetrofitProvider retrofitProvider = new RetrofitProvider();

        FriendsApi friendsApi = retrofitProvider.getFriendsApi();
        friendsApi.getAllFriends(accessToken, "5.80", "photo_200_orig").

                enqueue(new Callback<FriendsGetResponse>() {
                    @Override
                    public void onResponse
                            (@NonNull Call<FriendsGetResponse> call, @NonNull Response<FriendsGetResponse> response) {
                        if (response.isSuccessful()) {
                            FriendsGetResponse body = response.body();
                            Log.d("successful", body.toString());
                            mAdapter.setFriends(body.response.items);
                            swipeRefreshLayout.setRefreshing(false);

                        }
                    }

                    @Override
                    public void onFailure(Call<FriendsGetResponse> call, Throwable t) {
                        Log.d("dsvs", "");
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });

        RecyclerView mRecyclerView = findViewById(R.id.recyclerview_friends);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        int dimenSide = (int) getResources().getDimension(R.dimen.margin_side);
        int dimenTop = (int) getResources().getDimension(R.dimen.margin_top);
        mRecyclerView.addItemDecoration(new

                MarginItemDecoration(dimenSide, dimenTop));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    public void onButtonDeleteFriend(final long id) {
        String title = "Отправить в подписчики?";
        String buttonDeleteFriend = "Удалить из друзей";
        String buttonCancel = "Отмена";

        ad = new AlertDialog.Builder(FriendsActivity.this);
        ad.setTitle(title);
        ad.setPositiveButton(buttonDeleteFriend, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                Toast.makeText(FriendsActivity.this, "Вы сделали правильный выбор",
                        Toast.LENGTH_LONG).show();
                onDeleteFriend(id);
                loadData();
            }
        });
        ad.setNegativeButton(buttonCancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                Toast.makeText(FriendsActivity.this, "Возможно вы правы", Toast.LENGTH_LONG)
                        .show();
            }
        });
        ad.setCancelable(true);
        ad.show();
    }

    public void onDeleteFriend(long id) {
        RetrofitProvider retrofitProvider = new RetrofitProvider();
        FriendsApi friendsApi = retrofitProvider.getFriendsApi();
        friendsApi.getDeleteFriend(accessToken, "5.80", id).enqueue(new Callback<FriendsGetDeleteResponse>() {
            @Override
            public void onResponse(Call<FriendsGetDeleteResponse> call, Response<FriendsGetDeleteResponse> response) {
                if (response.isSuccessful()) {
                    FriendsGetDeleteResponse body = response.body();
                    Log.d("successful", body.toString());

                }
            }

            @Override
            public void onFailure(Call<FriendsGetDeleteResponse> call, Throwable t) {
                Log.d("lolkek", "lolec");
            }

        });


    }

    public void onUserGetInfo(long id) {
        Intent intent = new Intent(FriendsActivity.this, UserProfileActivity.class);
        intent.putExtra("idUser", id);
        startActivity(intent);

    }

}