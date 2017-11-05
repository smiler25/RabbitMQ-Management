package com.smiler.rabbitmanagement;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.smiler.rabbitmanagement.channels.ChannelsRecyclerFragment;
import com.smiler.rabbitmanagement.connections.ConnectionsRecyclerFragment;
import com.smiler.rabbitmanagement.overview.OverviewFragment;
import com.smiler.rabbitmanagement.queues.QueuesRecyclerFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private MainActivity mainActivity;
    public static final int POSITION_OVERVIEW = 0;
    public static final int POSITION_QUEUES = 1;
    public static final int POSITION_CONNECTIONS = 2;
    public static final int POSITION_CHANNELS = 3;

    SectionsPagerAdapter(MainActivity mainActivity, FragmentManager fm) {
        super(fm);
        this.mainActivity = mainActivity;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case POSITION_OVERVIEW:
                return OverviewFragment.newInstance();
            case POSITION_QUEUES:
                return QueuesRecyclerFragment.newInstance();
            case POSITION_CONNECTIONS:
                return ConnectionsRecyclerFragment.newInstance();
            case POSITION_CHANNELS:
                return ChannelsRecyclerFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case POSITION_OVERVIEW:
                return mainActivity.getString(R.string.overview);
            case POSITION_QUEUES:
                return mainActivity.getString(R.string.queues);
            case POSITION_CONNECTIONS:
                return mainActivity.getString(R.string.connections);
            case POSITION_CHANNELS:
                return mainActivity.getString(R.string.channels);
        }
        return null;
    }

    public String getFragmentTag(int viewPagerId, int fragmentPosition) {
        return "android:switcher:" + viewPagerId + ":" + fragmentPosition;
    }
}
