package com.example.karyc.vkontaktikum;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FriendsApi {
    @GET("friends.get")
    Call<FriendsGetResponse> getAllFriends(@Query("access_token") String accessToken,
                                           @Query("v") String v,
                                           @Query("fields") String fields);

    @GET("friends.getOnline")
    Call<FriendsOnlineGetResponse> getOnlineFriends(@Query("online_mobile") int onlineMobile,
                                                    @Query("access_token") String accessToken,
                                                    @Query("v") String v);

    @GET("friends.delete")
    Call<FriendsGetDeleteResponse> getDeleteFriend(@Query("access_token") String accessToken,
                                                   @Query("v") String v,
                                                   @Query("user_id") long userId);

    @GET("users.get")
    Call<UsersGetResponse> getUserResponse (@Query("access_token") String accessToken,
                                            @Query("v") String v,
                                            @Query("fields") String fields,
                                            @Query("user_ids") long id);
}
