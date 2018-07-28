package com.example.karyc.vkontaktikum;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Adapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.karyc.vkontaktikum.LoginActivity.SAVED_ACCESS_TOKEN;

public class FriendsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private FriendsAdapter mAdapter = new FriendsAdapter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        SharedPreferences sharedPreferences = getSharedPreferences("ACCESS_TOKEN_STORAGE", MODE_PRIVATE);
        String accessToken = sharedPreferences.getString(SAVED_ACCESS_TOKEN, null);
        RetrofitProvider retrofitProvider = new RetrofitProvider();

        FriendsApi friendsApi = retrofitProvider.getOnlineFriends();
        friendsApi.getOnlineFriends(1, accessToken, "5.80").enqueue(new Callback<FriendsOnlineGetResponse>() {
            @Override
            public void onResponse(Call<FriendsOnlineGetResponse> call, Response<FriendsOnlineGetResponse> response) {
                if (response.isSuccessful()){
                    Log.d("succ", response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<FriendsOnlineGetResponse> call, Throwable t) {

            }
        });

        friendsApi.getAllFriends(accessToken, "5.80", "photo_200_orig").enqueue(new Callback<FriendsGetResponse>() {
            @Override
            public void onResponse(Call<FriendsGetResponse> call, Response<FriendsGetResponse> response) {
                if (response.isSuccessful()) {
                    FriendsGetResponse body = response.body();
                    Log.d("successful", body.toString());
                    mAdapter.setFriends(body.response.items);
                }

            }

            @Override
            public void onFailure(Call<FriendsGetResponse> call, Throwable t) {
                Log.d("dsvs","" );
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_friends);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


    }
}
