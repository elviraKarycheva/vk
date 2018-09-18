package com.example.karyc.vkontaktikum.core.network;

import com.example.karyc.vkontaktikum.core.network.responseObjects.CommonResponse;
import com.example.karyc.vkontaktikum.core.network.responseObjects.GetFriendsResponse;
import com.example.karyc.vkontaktikum.core.network.responseObjects.ResponseFriendDelete;
import com.example.karyc.vkontaktikum.core.network.responseObjects.ResponseOnlineFriends;
import com.example.karyc.vkontaktikum.core.network.responseObjects.ResponseUsersGet;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FriendsApi {
    @GET("friends.get")
    Single<CommonResponse<GetFriendsResponse>> getAllFriends(@Query("access_token") String accessToken,
                                                             @Query("v") String v,
                                                             @Query("fields") String fields);

    @GET("friends.getOnline")
    Call<CommonResponse<ResponseOnlineFriends>> getOnlineFriends(@Query("online_mobile") int onlineMobile,
                                                                 @Query("access_token") String accessToken,
                                                                 @Query("v") String v);

    @GET("friends.delete")
    Single<CommonResponse<ResponseFriendDelete>> getDeleteFriend(@Query("access_token") String accessToken,
                                                               @Query("v") String v,
                                                               @Query("user_id") long userId);

    @GET("users.get")
    Single<CommonResponse<List<ResponseUsersGet>>> getUserResponse (@Query("access_token") String accessToken,
                                                                 @Query("v") String v,
                                                                 @Query("fields") String fields,
                                                                 @Query("user_ids") long id);
}
