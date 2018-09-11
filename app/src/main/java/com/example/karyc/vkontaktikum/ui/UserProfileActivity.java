package com.example.karyc.vkontaktikum.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.karyc.vkontaktikum.R;
import com.example.karyc.vkontaktikum.core.RetrofitProvider;
import com.example.karyc.vkontaktikum.core.network.FriendsApi;
import com.example.karyc.vkontaktikum.core.network.responseObjects.CommonResponse;
import com.example.karyc.vkontaktikum.core.network.responseObjects.ResponseUsersGet;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.karyc.vkontaktikum.ui.LoginActivity.SAVED_ACCESS_TOKEN;
import static com.example.karyc.vkontaktikum.ui.friends.AllFriendsFragment.ID_USER;

public class UserProfileActivity extends AppCompatActivity {
    TextView userNameView;
    TextView statusUserView;
    TextView siteUserView;
    ImageView imageUserProfileView;
    private String accessToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Intent intent = getIntent();
        long id = intent.getLongExtra(ID_USER, 0);

        userNameView = findViewById(R.id.userNameView);
        statusUserView = findViewById(R.id.userStatusView);
        siteUserView = findViewById(R.id.siteUserProfileView);
        imageUserProfileView = findViewById(R.id.userImageProfileView);

        SharedPreferences sharedPreferences = getSharedPreferences("ACCESS_TOKEN_STORAGE", MODE_PRIVATE);
        accessToken = sharedPreferences.getString(SAVED_ACCESS_TOKEN, null);

        FriendsApi friendsApi = RetrofitProvider.getFriendsApi();
        friendsApi.getUserResponse(accessToken, "5.80", "photo_200_orig,status,site,online", id)
                .enqueue(new Callback<CommonResponse<List<ResponseUsersGet>>>() {
                    @Override
                    public void onFailure(Call<CommonResponse<List<ResponseUsersGet>>> call, Throwable t) {

                    }

                    @Override
                    public void onResponse(Call<CommonResponse<List<ResponseUsersGet>>> call, Response<CommonResponse<List<ResponseUsersGet>>> response) {
                        if (response.isSuccessful()) {
                            CommonResponse<List<ResponseUsersGet>> body = response.body();
                            Log.d("successfulinfo", body.toString());

                            ResponseUsersGet friendInfo = body.response.get(0);

                            userNameView.setText(friendInfo.getFirstName() + " " + friendInfo.getLastName());
                            statusUserView.setText(friendInfo.getStatus());
                            siteUserView.setText(friendInfo.getSite());
                            Picasso.get()
                                    .load(friendInfo.getPhotoProfile())
                                    .into(imageUserProfileView);
                        }

                    }
                });


    }


}
