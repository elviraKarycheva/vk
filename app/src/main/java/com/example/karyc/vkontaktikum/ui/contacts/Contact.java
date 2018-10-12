package com.example.karyc.vkontaktikum.ui.contacts;

import android.support.annotation.NonNull;

import java.util.ArrayList;

public class Contact {

    private String name;
    private String phone;

    public Contact(@NonNull String name,@NonNull String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
