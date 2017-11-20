package com.smiler.rabbitmanagement;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.smiler.rabbitmanagement.profiles.Profile;
import com.smiler.rabbitmanagement.queues.filter.Filter;

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
        if (!profile.getStoreCredentials()) {
            profile = new Profile(profile, false);
        }
        db.profileDao().insert(profile);
    }

    public void insertFilter(Filter filter) {
        db.filterDao().insert(filter);
    }

    public LiveData<List<Profile>> getAllProfiles() {
        return db.profileDao().getAll();
    }

    public List<Profile> getAllProfilesSync() {
        return db.profileDao().getAllSync();
    }
}
