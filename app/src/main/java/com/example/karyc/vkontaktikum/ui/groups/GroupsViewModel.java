package com.example.karyc.vkontaktikum.ui.groups;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.example.karyc.vkontaktikum.App;
import com.example.karyc.vkontaktikum.core.Group;
import com.example.karyc.vkontaktikum.core.RetrofitProvider;
import com.example.karyc.vkontaktikum.core.network.GroupsApi;
import com.example.karyc.vkontaktikum.core.network.responseObjects.CommonResponse;
import com.example.karyc.vkontaktikum.core.network.responseObjects.ResponseGroupLeave;
import com.example.karyc.vkontaktikum.core.network.responseObjects.ResponseGroups;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static android.content.Context.MODE_PRIVATE;
import static com.example.karyc.vkontaktikum.ui.LoginActivity.SAVED_ACCESS_TOKEN;

public class GroupsViewModel extends AndroidViewModel {
    private String accessToken;
    private MutableLiveData<ArrayList<Group>> groupsLiveData = new MutableLiveData<>();

    LiveData<ArrayList<Group>> getGroupsLiveData() {
        return groupsLiveData;
    }

    public GroupsViewModel(@NonNull Application application) {
        super(application);
    }

    void loadData() {

        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("ACCESS_TOKEN_STORAGE", MODE_PRIVATE);
        accessToken = sharedPreferences.getString(SAVED_ACCESS_TOKEN, null);

        GroupsApi groupsApi = RetrofitProvider.getGroupsApi();
        groupsApi
                .getAllGroups(accessToken, "5.80", "groups, publics", 1)
                .map(new Function<CommonResponse<ResponseGroups>, CommonResponse<ResponseGroups>>() {
                    @Override
                    public CommonResponse<ResponseGroups> apply(CommonResponse<ResponseGroups> responseGroupsCommonResponse) throws Exception {
                        App.getDatabase().getGroupDao().insert(responseGroupsCommonResponse.response.items);
                        return responseGroupsCommonResponse;
                    }
                })
                .map(new Function<CommonResponse<ResponseGroups>, ArrayList<Group>>() {
                    @Override
                    public ArrayList<Group> apply(CommonResponse<ResponseGroups> responseGroupsCommonResponse) throws Exception {
                        return responseGroupsCommonResponse.response.items;
                    }
                })
                .onErrorResumeNext(new Function<Throwable, SingleSource<? extends ArrayList<Group>>>() {
                    @Override
                    public SingleSource<? extends ArrayList<Group>> apply(Throwable throwable) throws Exception {
                        return Single.create(new SingleOnSubscribe<ArrayList<Group>>() {
                            @Override
                            public void subscribe(SingleEmitter<ArrayList<Group>> e) throws Exception {
                                List<Group> allGroup = App.getDatabase().getGroupDao().getAllGroup();
                                e.onSuccess(new ArrayList<>(allGroup));
                            }
                        });
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ArrayList<Group>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ArrayList<Group> groups) {
                        groupsLiveData.setValue(groups);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    void onDeleteGroup(long id) {
        GroupsApi groupsApi = RetrofitProvider.getGroupsApi();
        groupsApi
                .getLeaveGroup(accessToken, "5.80", id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<CommonResponse<ResponseGroupLeave>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(CommonResponse<ResponseGroupLeave> responseGroupLeaveCommonResponse) {
                        loadData();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
}
