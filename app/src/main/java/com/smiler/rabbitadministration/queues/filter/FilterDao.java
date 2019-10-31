package com.smiler.rabbitadministration.queues.filter;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface FilterDao {

    @Query("SELECT * FROM queue_filter")
    LiveData<List<Filter>> getAll();

    @Query("SELECT * FROM queue_filter")
    List<Filter> getAllSync();

    @Query("SELECT * FROM queue_filter WHERE id LIKE :id LIMIT 1")
    Filter findById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Filter filter);

    @Delete
    void delete(Filter filter);
}
