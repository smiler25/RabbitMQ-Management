package com.smiler.rabbitadministration.profiles;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ProfileDao {

    @Query("SELECT * FROM profile")
    LiveData<List<Profile>> getAll();

    @Query("SELECT * FROM profile")
    List<Profile> getAllSync();

    @Query("SELECT * FROM profile WHERE id LIKE :id LIMIT 1")
    Profile findById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Profile profile);

    @Delete
    void delete(Profile profile);

    @Update
    void update(Profile... profiles);
}