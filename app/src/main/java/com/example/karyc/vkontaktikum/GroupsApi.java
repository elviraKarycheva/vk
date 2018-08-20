package com.example.karyc.vkontaktikum;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GroupsApi {
    @GET("groups.get")
    Call<GroupsGetResponse> getAllGroups(@Query("access_token") String accessToken,
                                         @Query("v") String v,
                                         @Query("filter") String filter,
                                         @Query("extended") int extended);

    @GET("groups.leave")
    Call<GroupLeaveResponse> getLeaveGroup (@Query("access_token") String accessToken,
                                            @Query("v") String v,
                                            @Query("group_id") long id);
}
