package com.example.karyc.vkontaktikum;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.karyc.vkontaktikum.LoginActivity.SAVED_ACCESS_TOKEN;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("ACCESS_TOKEN_STORAGE", MODE_PRIVATE);
        String accessToken = sharedPreferences.getString(SAVED_ACCESS_TOKEN, null);
        RetrofitProvider retrofitProvider = new RetrofitProvider();
        FriendsApi friendsApi = retrofitProvider.getFriendsApi();
        friendsApi.getAllFriends(accessToken, "5.80").enqueue(new Callback<FriendsGetResponse>() {
            @Override
            public void onResponse(Call<FriendsGetResponse> call, Response<FriendsGetResponse> response) {
                if (response.isSuccessful()){
                    Log.d("successful", response.body().toString());
                }

            }

            @Override
            public void onFailure(Call<FriendsGetResponse> call, Throwable t) {

            }
        });
    }
}
