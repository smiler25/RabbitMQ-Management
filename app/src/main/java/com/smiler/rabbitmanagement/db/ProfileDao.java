package com.smiler.rabbitmanagement.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ProfileDao {

    @Query("SELECT * FROM profile")
    LiveData<List<Profile>> getAll();

    @Query("SELECT * FROM profile WHERE title LIKE :title LIMIT 1")
    Profile findByTitle(String title);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Profile profile);

    @Delete
    void delete(Profile profile);
}
