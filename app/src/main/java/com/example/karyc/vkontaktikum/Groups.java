package com.example.karyc.vkontaktikum;

import com.google.gson.annotations.SerializedName;

public class Groups {
    private long id;
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
