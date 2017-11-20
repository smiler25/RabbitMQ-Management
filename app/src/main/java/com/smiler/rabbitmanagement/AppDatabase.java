package com.smiler.rabbitmanagement;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.smiler.rabbitmanagement.profiles.Profile;
import com.smiler.rabbitmanagement.profiles.ProfileDao;
import com.smiler.rabbitmanagement.queues.filter.Filter;
import com.smiler.rabbitmanagement.queues.filter.FilterDao;

@Database(entities = {Profile.class, Filter.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ProfileDao profileDao();
    public abstract FilterDao filterDao();

}