package com.example.karyc.vkontaktikum;

import com.google.gson.annotations.SerializedName;

public class ResponseUsersGet {
    private long id;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("photo_200_orig")
    private String photoProfile;
    private int online;
    private String site;
    private String status;

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

    public String getSite() {
        return site;
    }

    public String getStatus() {
        return status;
    }
}
