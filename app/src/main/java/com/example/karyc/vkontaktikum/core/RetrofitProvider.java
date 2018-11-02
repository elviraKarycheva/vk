package com.example.karyc.vkontaktikum.core;

import com.example.karyc.vkontaktikum.core.network.FriendsApi;
import com.example.karyc.vkontaktikum.core.network.GroupsApi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitProvider {
    private static final String BASE_URL = "https://api.vk.com/method/";

    private static HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private static OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();

    public static FriendsApi getFriendsApi() {
        return retrofit.create(FriendsApi.class);
    }

    public static GroupsApi getGroupsApi() {
        return retrofit.create(GroupsApi.class);
    }
}
