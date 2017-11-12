package com.smiler.rabbitmanagement;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.smiler.rabbitmanagement.db.Profile;
import com.smiler.rabbitmanagement.db.ProfileDao;

@Database(entities = {Profile.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ProfileDao profileDao();

}