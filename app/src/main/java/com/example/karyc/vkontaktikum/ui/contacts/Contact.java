package com.example.karyc.vkontaktikum.ui.contacts;

import android.support.annotation.NonNull;

public class Contact {

    private String name;
    private String phone;
    private String photo;

    public Contact(@NonNull String name,@NonNull String phone, @NonNull String photo) {
        this.name = name;
        this.phone = phone;
        this.photo = photo;

    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getPhoto() {
        return photo;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
