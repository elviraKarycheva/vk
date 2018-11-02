package com.example.karyc.vkontaktikum;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.karyc.vkontaktikum.core.database.AppDatabase;

public class App extends Application {

    private static AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(this, AppDatabase.class, "database")
                .fallbackToDestructiveMigration()
                .build();
    }

    public static AppDatabase getDatabase() {
        return database;
    }
}
