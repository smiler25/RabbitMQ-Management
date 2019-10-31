package com.smiler.rabbitadministration;

import com.smiler.rabbitadministration.profiles.Profile;
import com.smiler.rabbitadministration.profiles.ProfileDao;
import com.smiler.rabbitadministration.queues.filter.Filter;
import com.smiler.rabbitadministration.queues.filter.FilterDao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Profile.class, Filter.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ProfileDao profileDao();
    public abstract FilterDao filterDao();

}