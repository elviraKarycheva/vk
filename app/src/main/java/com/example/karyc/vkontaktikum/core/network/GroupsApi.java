package com.example.karyc.vkontaktikum.core.network;

import com.example.karyc.vkontaktikum.core.network.responseObjects.CommonResponse;
import com.example.karyc.vkontaktikum.core.network.responseObjects.ResponseGroupLeave;
import com.example.karyc.vkontaktikum.core.network.responseObjects.ResponseGroups;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GroupsApi {
    @GET("groups.get")
    Single<CommonResponse<ResponseGroups>> getAllGroups(@Query("access_token") String accessToken,
                                                        @Query("v") String v,
                                                        @Query("filter") String filter,
                                                        @Query("extended") int extended);

    @GET("groups.leave")
    Single<CommonResponse<ResponseGroupLeave>> getLeaveGroup (@Query("access_token") String accessToken,
                                                            @Query("v") String v,
                                                            @Query("group_id") long id);
}
