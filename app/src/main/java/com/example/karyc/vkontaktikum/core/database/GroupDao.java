package com.example.karyc.vkontaktikum.core.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.karyc.vkontaktikum.core.Group;

import java.util.List;

@Dao
public interface GroupDao {
    @Query("SELECT * FROM `Group`")
    List<Group> getAllGroup();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Group> groups);

    @Query("SELECT * FROM `Group` WHERE name = :name")
    List<Group> getByName(String name);

    @Update
    void update(Group group);

    @Query("DELETE FROM `Group` WHERE id =:id")
    void delete(long id);

    @Query("DELETE FROM `Group`")
    void deleteAll();
}
