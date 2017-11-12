package com.smiler.rabbitmanagement.queues.filter;

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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Filter filter);

    @Delete
    void delete(Filter filter);
}