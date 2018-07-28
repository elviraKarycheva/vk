package com.example.karyc.vkontaktikum;

import java.util.ArrayList;

public class ResponseOnlineFriends {

    public ArrayList<Long> online;
    public ArrayList<Long> online_mobile;

    @Override
    public String toString() {
        return "ResponseOnlineFriends{" +
                "online=" + online +
                ", online_mobile=" + online_mobile +
                '}';
    }
}
