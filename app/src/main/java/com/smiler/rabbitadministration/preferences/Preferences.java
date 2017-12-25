package com.smiler.rabbitadministration.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Preferences {
    private static Preferences instance;
    private final SharedPreferences prefs;

    @Setter
    private boolean afterCreate;
    private boolean loadOnOpen;
    private boolean saveActiveProfile;
    private boolean saveActiveFilter;
    private boolean saveActiveSort;
    private boolean saveActiveProfileChanged;
    private boolean saveActiveFilterChanged;
    private boolean saveActiveSortChanged;

    public static Preferences getInstance(Context context){
        if (instance == null){
            instance = new Preferences(context);
        }
        return instance;
    }

    private Preferences(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void read() {
        loadOnOpen = prefs.getBoolean(PrefActivity.PREF_LOAD_ON_OPEN, true);
        boolean tmpProfile_ = prefs.getBoolean(PrefActivity.PREF_SAVE_ACTIVE_PROFILE, true);
        if (saveActiveProfile != tmpProfile_) {
            saveActiveProfileChanged = true;
            saveActiveProfile = tmpProfile_;
        }

        boolean tmpFilter_ = prefs.getBoolean(PrefActivity.PREF_SAVE_ACTIVE_FILTER, true);
        if (saveActiveFilter != tmpFilter_) {
            saveActiveFilterChanged = true;
            saveActiveFilter = tmpFilter_;
        }

        boolean tmpSort_ = prefs.getBoolean(PrefActivity.PREF_SAVE_ACTIVE_SORT, true);
        if (saveActiveSort != tmpSort_) {
            saveActiveSortChanged = true;
            saveActiveSort = tmpSort_;
        }
    }

    public void resetChangeStates() {
        saveActiveProfileChanged = false;
        saveActiveFilterChanged = false;
        saveActiveSortChanged = false;
    }
}
