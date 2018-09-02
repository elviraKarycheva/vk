package com.example.karyc.vkontaktikum;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.karyc.vkontaktikum.LoginActivity.SAVED_ACCESS_TOKEN;

public class GroupsActivity extends AppCompatActivity {
    private GroupsAdapter mAdapter = new GroupsAdapter();
    private String accessToken;
    private AlertDialog.Builder ad;
    private SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        swipeRefreshLayout = findViewById(R.id.swipe_groups);
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
        mAdapter.groupsActivity = this;
        SharedPreferences sharedPreferences = getSharedPreferences("ACCESS_TOKEN_STORAGE", MODE_PRIVATE);
        accessToken = sharedPreferences.getString(SAVED_ACCESS_TOKEN, null);
        RetrofitProvider retrofitProvider = new RetrofitProvider();

        GroupsApi groupsApi = retrofitProvider.getGroupsApi();
        groupsApi.getAllGroups(accessToken, "5.80", "groups, publics", 1).enqueue(new Callback<GroupsGetResponse>() {
            @Override
            public void onResponse(Call<GroupsGetResponse> call, Response<GroupsGetResponse> response) {
                if (response.isSuccessful()) {
                    GroupsGetResponse body = response.body();
                    Log.d("groups", body.toString());
                    mAdapter.setGroups(body.response.items);
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<GroupsGetResponse> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);


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

    public void onButtonDeleteGroup(final long id) {

        String title = "Выйти из группы?";
        String leaveGruop = "Покинуть группу";
        String buttonCancel = "Отмена";

        ad = new AlertDialog.Builder(GroupsActivity.this);
        ad.setTitle(title);
        ad.setPositiveButton(leaveGruop, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                Toast.makeText(GroupsActivity.this, "Вы сделали правильный выбор",
                        Toast.LENGTH_LONG).show();
                onDeleteGroup(id);
                loadData();
            }
        });
        ad.setNegativeButton(buttonCancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                Toast.makeText(GroupsActivity.this, "Возможно вы правы", Toast.LENGTH_LONG)
                        .show();
            }
        });
        ad.setCancelable(true);
        ad.show();
    }

    public void onDeleteGroup(long id) {
        RetrofitProvider retrofitProvider = new RetrofitProvider();
        GroupsApi groupsApi = retrofitProvider.getGroupsApi();
        groupsApi.getLeaveGroup(accessToken, "5.80", id).enqueue(new Callback<GroupLeaveResponse>() {
            @Override
            public void onResponse(Call<GroupLeaveResponse> call, Response<GroupLeaveResponse> response) {
                if (response.isSuccessful()) {
                    GroupLeaveResponse body = response.body();
                    Log.d("successful", body.toString());
                }
            }

            @Override
            public void onFailure(Call<GroupLeaveResponse> call, Throwable t) {

            }
        });
    }
}
