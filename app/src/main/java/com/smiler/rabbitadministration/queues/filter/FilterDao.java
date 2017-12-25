package com.smiler.rabbitadministration.queues.filter;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

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
