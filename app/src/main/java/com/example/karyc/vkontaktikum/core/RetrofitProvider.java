package com.example.karyc.vkontaktikum.core;

import com.example.karyc.vkontaktikum.core.network.FriendsApi;
import com.example.karyc.vkontaktikum.core.network.GroupsApi;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitProvider {
    private static final String BASE_URL = "https://api.vk.com/method/";

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    public static FriendsApi getFriendsApi() {
        return retrofit.create(FriendsApi.class);
    }
    public static GroupsApi getGroupsApi(){
        return retrofit.create(GroupsApi.class);
    }
}
