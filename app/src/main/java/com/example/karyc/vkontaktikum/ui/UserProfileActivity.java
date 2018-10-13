package com.example.karyc.vkontaktikum.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.karyc.vkontaktikum.R;
import com.example.karyc.vkontaktikum.core.RetrofitProvider;
import com.example.karyc.vkontaktikum.core.network.FriendsApi;
import com.example.karyc.vkontaktikum.core.network.responseObjects.CommonResponse;
import com.example.karyc.vkontaktikum.core.network.responseObjects.ResponseUsersGet;
import com.example.karyc.vkontaktikum.databinding.ActivityUserProfileBinding;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.karyc.vkontaktikum.ui.LoginActivity.SAVED_ACCESS_TOKEN;
import static com.example.karyc.vkontaktikum.ui.friends.FriendsFragment.ID_USER;

public class UserProfileActivity extends AppCompatActivity {
    private String accessToken;
    private ActivityUserProfileBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_profile);

        Intent intent = getIntent();
        long id = intent.getLongExtra(ID_USER, 0);

        SharedPreferences sharedPreferences = getSharedPreferences("ACCESS_TOKEN_STORAGE", MODE_PRIVATE);
        accessToken = sharedPreferences.getString(SAVED_ACCESS_TOKEN, null);

        FriendsApi friendsApi = RetrofitProvider.getFriendsApi();
        friendsApi
                .getUserResponse(accessToken, "5.80", "photo_200_orig,status,site,online", id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CommonResponse<List<ResponseUsersGet>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(CommonResponse<List<ResponseUsersGet>> listCommonResponse) {
                        ResponseUsersGet friendInfo = listCommonResponse.response.get(0);

                        binding.setUser(friendInfo);
                        binding.executePendingBindings();
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }
}
