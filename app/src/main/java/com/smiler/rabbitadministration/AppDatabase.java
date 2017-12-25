package com.smiler.rabbitadministration;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.smiler.rabbitadministration.profiles.Profile;
import com.smiler.rabbitadministration.profiles.ProfileDao;
import com.smiler.rabbitadministration.queues.filter.Filter;
import com.smiler.rabbitadministration.queues.filter.FilterDao;

@Database(entities = {Profile.class, Filter.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ProfileDao profileDao();
    public abstract FilterDao filterDao();

}