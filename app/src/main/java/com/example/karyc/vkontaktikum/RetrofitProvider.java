package com.example.karyc.vkontaktikum;

import android.os.Bundle;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitProvider {
    private static final String BASE_URL = "https://api.vk.com/method/";

    public FriendsApi getFriendsApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        FriendsApi friendsApi = retrofit.create(FriendsApi.class);
        return friendsApi;
    }
}
