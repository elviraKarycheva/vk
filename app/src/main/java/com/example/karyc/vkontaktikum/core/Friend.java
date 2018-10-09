package com.example.karyc.vkontaktikum.core;

import com.google.gson.annotations.SerializedName;

public class Friend {
    private long id;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("photo_200_orig")
    private String photoProfile;
    private int online;
    @SerializedName("mobile_phone")
    private String mobilePhone;

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhotoProfile() {
        return photoProfile;
    }

    public int getOnline() {
        return online;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }
}
