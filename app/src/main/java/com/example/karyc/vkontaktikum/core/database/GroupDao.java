package com.example.karyc.vkontaktikum.core.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.karyc.vkontaktikum.core.Group;

import java.util.List;

@Dao
public interface GroupDao {
    @Query("SELECT * FROM `Group`")
    List<Group> getAllGroup();

    @Insert
    void insert(List<Group> groups);

    @Query("SELECT * FROM `Group` WHERE name = :name")
    List<Group> getByName(String name);
}
