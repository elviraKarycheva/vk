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
import java.util.Collections;
import java.util.Comparator;
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
                        App.getDatabase().getGroupDao().deleteAll();
                        for (int i = 0; i < responseGroupsCommonResponse.response.items.size(); i++) {
                            Group group = responseGroupsCommonResponse.response.items.get(i);
                            group.setOrder(i);
                        }
                        App.getDatabase().getGroupDao().insert(responseGroupsCommonResponse.response.items);
                        return responseGroupsCommonResponse;// положили в БД
                    }
                })
                .map(new Function<CommonResponse<ResponseGroups>, ArrayList<Group>>() {
                    @Override
                    public ArrayList<Group> apply(CommonResponse<ResponseGroups> responseGroupsCommonResponse) throws Exception {
                        return responseGroupsCommonResponse.response.items;// возвращаем именно arrayList
                    }
                })
                .onErrorResumeNext(new Function<Throwable, SingleSource<? extends ArrayList<Group>>>() {
                    @Override
                    public SingleSource<? extends ArrayList<Group>> apply(Throwable throwable) throws Exception {
                        return Single.create(new SingleOnSubscribe<ArrayList<Group>>() {
                            @Override
                            public void subscribe(SingleEmitter<ArrayList<Group>> e) throws Exception {
                                List<Group> allGroup = App.getDatabase().getGroupDao().getAllGroup();// если нет сети - берем из БД
                                e.onSuccess(new ArrayList<>(allGroup));
                            }
                        });
                    }
                })
                .map(new Function<ArrayList<Group>, ArrayList<Group>>() {
                    @Override
                    public ArrayList<Group> apply(ArrayList<Group> groups) throws Exception {
                        Collections.sort(groups, new Comparator<Group>() {
                            @Override
                            public int compare(Group o1, Group o2) {
                                if (o1.getOrder() == o2.getOrder()) {
                                    return 0;
                                }
                                if (o1.getOrder() < o2.getOrder()) {
                                    return -1;
                                } else {
                                    return 1;
                                }
                            }
                        });
                        return groups;
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
                        e.printStackTrace();
                    }
                });
    }

    void onDeleteGroup(final long id) {
        final GroupsApi groupsApi = RetrofitProvider.getGroupsApi();
        groupsApi
                .getLeaveGroup(accessToken, "5.80", id)
                .map(new Function<CommonResponse<ResponseGroupLeave>, CommonResponse<ResponseGroupLeave>>() {
                    @Override
                    public CommonResponse<ResponseGroupLeave> apply(CommonResponse<ResponseGroupLeave> responseGroupLeaveCommonResponse) throws Exception {
                        App.getDatabase().getGroupDao().delete(id);
                        return responseGroupLeaveCommonResponse; //удаление из кэша при условии что запрос успешный
                    }
                })
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
                        e.printStackTrace();
                    }
                });
    }
}
