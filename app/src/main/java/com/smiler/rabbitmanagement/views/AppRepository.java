package com.smiler.rabbitmanagement.views;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.smiler.rabbitmanagement.AppDatabase;
import com.smiler.rabbitmanagement.db.Profile;

import java.util.List;

public class AppRepository {
    private static AppRepository instance;
    private AppDatabase db;

    public static AppRepository getInstance(Context context){
        if (instance == null){
            instance = new AppRepository(context);
        }
        return instance;
    }

    private AppRepository(Context context) {
        db = Room.databaseBuilder(context, AppDatabase.class, "rmq-management-db")
                .allowMainThreadQueries().build();
    }

    public void insertProfile(Profile profile) {
        db.profileDao().insert(profile);
    }

    public LiveData<List<Profile>> getAllProfiles() {
        return db.profileDao().getAll();
    }
}
