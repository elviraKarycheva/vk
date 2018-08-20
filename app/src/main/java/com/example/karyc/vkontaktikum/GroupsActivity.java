package com.example.karyc.vkontaktikum;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.karyc.vkontaktikum.LoginActivity.SAVED_ACCESS_TOKEN;

public class GroupsActivity extends AppCompatActivity{
    private GroupsAdapter mAdapter = new GroupsAdapter();
    private String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        mAdapter.groupsActivity = this;
        SharedPreferences sharedPreferences = getSharedPreferences("ACCESS_TOKEN_STORAGE", MODE_PRIVATE);
        accessToken = sharedPreferences.getString(SAVED_ACCESS_TOKEN, null);
        RetrofitProvider retrofitProvider = new RetrofitProvider();

        GroupsApi groupsApi = retrofitProvider.getGroupsApi();
        groupsApi.getAllGroups(accessToken, "5.80", "groups, publics", 1).enqueue(new Callback<GroupsGetResponse>() {
            @Override
            public void onResponse(Call<GroupsGetResponse> call, Response<GroupsGetResponse> response) {
                if (response.isSuccessful()){
                    GroupsGetResponse body = response.body();
                    Log.d("groups", body.toString());
                    mAdapter.setGroups(body.response.items);
                }
            }

            @Override
            public void onFailure(Call<GroupsGetResponse> call, Throwable t) {

            }
        });
        RecyclerView mRecyclerView = findViewById(R.id.recyclerview_groups);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        int dimenSide = (int) getResources().getDimension(R.dimen.margin_side);
        int dimenTop = (int) getResources().getDimension(R.dimen.margin_top);
        mRecyclerView.addItemDecoration(new MarginItemDecoration(dimenSide, dimenTop));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }
}
