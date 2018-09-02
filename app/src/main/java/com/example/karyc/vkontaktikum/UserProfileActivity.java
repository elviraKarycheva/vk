package com.example.karyc.vkontaktikum;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.karyc.vkontaktikum.LoginActivity.SAVED_ACCESS_TOKEN;

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
        long id = intent.getLongExtra("idUser", 0);

        userNameView = findViewById(R.id.userNameView);
        statusUserView = findViewById(R.id.userStatusView);
        siteUserView = findViewById(R.id.siteUserProfileView);
        imageUserProfileView = findViewById(R.id.userImageProfileView);

        SharedPreferences sharedPreferences = getSharedPreferences("ACCESS_TOKEN_STORAGE", MODE_PRIVATE);
        accessToken = sharedPreferences.getString(SAVED_ACCESS_TOKEN, null);

        RetrofitProvider retrofitProvider = new RetrofitProvider();
        FriendsApi friendsApi = retrofitProvider.getFriendsApi();
        friendsApi.getUserResponse(accessToken, "5.80", "photo_200_orig,status,site,online", id)
                .enqueue(new Callback<UsersGetResponse>() {
                    @Override
                    public void onResponse(Call<UsersGetResponse> call, Response<UsersGetResponse> responseResponse) {
                        if (responseResponse.isSuccessful()) {
                            UsersGetResponse body = responseResponse.body();
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

                    @Override
                    public void onFailure(Call<UsersGetResponse> call, Throwable t) {
                        Log.d("sadness", "lolec");
                    }
                });


    }


}
