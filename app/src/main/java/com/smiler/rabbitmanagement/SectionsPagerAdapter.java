package com.smiler.rabbitmanagement;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.smiler.rabbitmanagement.overview.OverviewFragment;
import com.smiler.rabbitmanagement.queues.QueuesRecyclerFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private MainActivity mainActivity;

    public SectionsPagerAdapter(MainActivity mainActivity, FragmentManager fm) {
        super(fm);
        this.mainActivity = mainActivity;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return OverviewFragment.newInstance();
            case 1:
                return QueuesRecyclerFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mainActivity.getString(R.string.overview);
            case 1:
                return mainActivity.getString(R.string.queues);
        }
        return null;
    }
}
