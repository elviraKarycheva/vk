package com.example.karyc.vkontaktikum.core;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

@Entity
public class Group {
    public Group(long id, @NonNull String name, String screenName, int isClosed, String type, int isAdmin,
                 int isMember, String photo50, String photo100, String photo200) {
        this.id = id;
        this.name = name;
        this.screenName = screenName;
        this.isClosed = isClosed;
        this.type = type;
        this.isAdmin = isAdmin;
        this.isMember = isMember;
        this.photo50 = photo50;
        this.photo100 = photo100;
        this.photo200 = photo200;
    }

    private long id;
    @PrimaryKey
    @NonNull
    private String name;

    @SerializedName("screen_name")
    private String screenName;
    @SerializedName("is_closed")
    private int isClosed;
    private String type;
    @SerializedName("is_admin")
    private int isAdmin;
    @SerializedName("is_member")
    private int isMember;
    @SerializedName("photo_50")
    private String photo50;
    @SerializedName("photo_100")
    private String photo100;
    @SerializedName("photo_200")
    private String photo200;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getScreenName() {
        return screenName;
    }

    public int getIsClosed() {
        return isClosed;
    }

    public String getType() {
        return type;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public int getIsMember() {
        return isMember;
    }

    public String getPhoto50() {
        return photo50;
    }

    public String getPhoto100() {
        return photo100;
    }

    public String getPhoto200() {
        return photo200;
    }
}
