package com.example.karyc.vkontaktikum.ui.group_details;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.karyc.vkontaktikum.core.Group;

public class GroupDetailsViewModel extends AndroidViewModel {
    public GroupDetailsViewModel(@NonNull Application application) {
        super(application);
    }

    private MutableLiveData<Group> groupDetailsLiveData = new MutableLiveData<>();

    LiveData<Group> getGroupDetailsLiveData() {
        return groupDetailsLiveData;
    }

    void loadData() {


    }
}
