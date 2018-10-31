package com.example.karyc.vkontaktikum.ui.friends;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.example.karyc.vkontaktikum.core.Friend;
import com.example.karyc.vkontaktikum.core.RetrofitProvider;
import com.example.karyc.vkontaktikum.core.network.FriendsApi;
import com.example.karyc.vkontaktikum.core.network.responseObjects.CommonResponse;
import com.example.karyc.vkontaktikum.core.network.responseObjects.GetFriendsResponse;
import com.example.karyc.vkontaktikum.core.network.responseObjects.ResponseFriendDelete;

import java.util.ArrayList;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.Context.MODE_PRIVATE;
import static com.example.karyc.vkontaktikum.ui.LoginActivity.SAVED_ACCESS_TOKEN;

public class FriendsViewModel extends AndroidViewModel {
    private String accessToken;
    private MutableLiveData<ArrayList<Friend>> friendsLiveData = new MutableLiveData<>();

    public FriendsViewModel(@NonNull Application application) {
        super(application);
    }

    LiveData<ArrayList<Friend>> getFriendsLiveData() {
        return friendsLiveData;
    }

    void loadData(final boolean showOnline) {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("ACCESS_TOKEN_STORAGE", MODE_PRIVATE);
        accessToken = sharedPreferences.getString(SAVED_ACCESS_TOKEN, null);

        FriendsApi friendsApi = RetrofitProvider.getFriendsApi();
        friendsApi
                .getAllFriends(accessToken, "5.80", "photo_200_orig")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CommonResponse<GetFriendsResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(CommonResponse<GetFriendsResponse> getFriendsResponseCommonResponse) {
                        if (showOnline == true) {
                            ArrayList<Friend> allFriends = getFriendsResponseCommonResponse.response.items;
                            ArrayList<Friend> onlineFriends = new ArrayList<>();

                            for (Friend currentItem : allFriends) {
                                if (currentItem.getOnline() == 1) {
                                    onlineFriends.add(currentItem);
                                }
                            }
                            friendsLiveData.setValue(onlineFriends);

                        } else {
                            friendsLiveData.setValue(getFriendsResponseCommonResponse.response.items);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });

    }

     void onDeleteFriend(long id, final boolean showOnline) {
        FriendsApi friendsApi = RetrofitProvider.getFriendsApi();

        friendsApi
                .getDeleteFriend(accessToken, "5.80", id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CommonResponse<ResponseFriendDelete>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(CommonResponse<ResponseFriendDelete> responseFriendDeleteCommonResponse) {
                        loadData(showOnline);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
}
