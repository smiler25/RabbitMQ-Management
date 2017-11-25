package com.smiler.rabbitmanagement.preferences;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewParent;
import android.widget.LinearLayout;

import com.smiler.rabbitmanagement.R;


public class PrefActivity extends Activity {
    private static final String TAG = "RMQ-PrefActivity";

    public static final String PREF_LOAD_ON_OPEN = "load_on_open";
    public static final String PREF_SAVE_ACTIVE_PROFILE = "save_active_profile";
    public static final String PREF_SAVE_ACTIVE_FILTER = "save_active_filter";
    public static final String PREF_SAVE_ACTIVE_SORT = "save_active_sort";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new PrefFragment())
                .commit();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initToolbar();
    }

    private LinearLayout getRoot(ViewParent view) {
        ViewParent parent = view.getParent();
        if (parent instanceof LinearLayout) {
            return (LinearLayout) parent;
        }
        return getRoot(parent);
    }

    private void initToolbar() {
        try {
            LinearLayout root = getRoot(findViewById(android.R.id.list).getParent());
            Toolbar toolbar = (Toolbar) LayoutInflater.from(this).inflate(R.layout.toolbar_prefs, root, false);
            root.addView(toolbar, 0);
            toolbar.setNavigationOnClickListener(v -> finish());
        } catch (RuntimeException e) {
            Log.e(TAG, "Error init toolbar");
        }
    }
}
