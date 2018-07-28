package com.example.karyc.vkontaktikum;

import com.google.gson.annotations.SerializedName;

public class Friend {

    public long id;
    @SerializedName("first_name")
    public String firstName;
    @SerializedName("last_name")
    public String lastName;
    @SerializedName("photo_200_orig")
    public String photoProfile;
    public int online;


}
