package com.example.karyc.vkontaktikum;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FriendsApi {
    @GET ("friends.get")
    Call<FriendsGetResponse> getAllFriends(@Query("access_token") String accessToken,
                                           @Query("v") String v);
}
