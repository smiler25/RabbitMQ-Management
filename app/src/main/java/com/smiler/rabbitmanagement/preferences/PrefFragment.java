package com.smiler.rabbitmanagement.preferences;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.smiler.rabbitmanagement.R;


public class PrefFragment extends PreferenceFragment {
    @Override
    public  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_activity);
    }
}
