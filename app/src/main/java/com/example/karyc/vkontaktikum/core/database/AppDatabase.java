package com.example.karyc.vkontaktikum.core.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.karyc.vkontaktikum.core.Group;

@Database(entities = {Group.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract GroupDao getGroupDao();

}
