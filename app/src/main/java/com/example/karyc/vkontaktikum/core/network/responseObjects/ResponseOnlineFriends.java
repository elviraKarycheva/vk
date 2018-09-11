package com.example.karyc.vkontaktikum.core.network.responseObjects;

import com.example.karyc.vkontaktikum.core.Friend;

import java.util.ArrayList;

public class ResponseOnlineFriends {

    public ArrayList<Friend> online;
    public ArrayList<Long> online_mobile;

    @Override
    public String toString() {
        return "ResponseOnlineFriends{" +
                "online=" + online +
                ", online_mobile=" + online_mobile +
                '}';
    }
}
