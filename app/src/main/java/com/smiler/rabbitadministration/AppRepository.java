package com.smiler.rabbitadministration;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.smiler.rabbitadministration.profiles.Profile;
import com.smiler.rabbitadministration.queues.filter.Filter;

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

    public Profile insertProfile(Profile profile) {
        if (profile.getStoreCredentials() == null || !profile.getStoreCredentials()) {
            profile = new Profile(profile, false);
        }
        return db.profileDao().findById((int) db.profileDao().insert(profile));
    }

    public Profile getProfile(int id) {
        return db.profileDao().findById(id);
    }

    public List<Profile> getAllProfilesSync() {
        return db.profileDao().getAllSync();
    }

    public void deleteProfile(Profile profile) {
        db.profileDao().delete(profile);
    }

    public void updateProfile(Profile profile) {
        db.profileDao().update(profile);
    }

    public Filter insertFilter(Filter filter) {
        return db.filterDao().findById((int) db.filterDao().insert(filter));
    }

    public List<Filter> getAllFiltersSync() {
        return db.filterDao().getAllSync();
    }

    public Filter getFilter(int id) {
        return db.filterDao().findById(id);
    }
}