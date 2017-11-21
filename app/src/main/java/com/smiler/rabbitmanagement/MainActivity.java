package com.smiler.rabbitmanagement;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.smiler.rabbitmanagement.base.DetailFragment;
import com.smiler.rabbitmanagement.base.interfaces.FragmentListListener;
import com.smiler.rabbitmanagement.base.interfaces.UpdatableFragment;
import com.smiler.rabbitmanagement.channels.ChannelDetailFragment;
import com.smiler.rabbitmanagement.channels.ChannelsRecyclerFragment;
import com.smiler.rabbitmanagement.connections.ConnectionDetailFragment;
import com.smiler.rabbitmanagement.connections.ConnectionsRecyclerFragment;
import com.smiler.rabbitmanagement.detail.QueueDetailFragment;
import com.smiler.rabbitmanagement.overview.OverviewFragment;
import com.smiler.rabbitmanagement.profiles.Profile;
import com.smiler.rabbitmanagement.profiles.ProfileSelector;
import com.smiler.rabbitmanagement.queues.QueuesListViewModel;
import com.smiler.rabbitmanagement.queues.QueuesRecyclerFragment;
import com.smiler.rabbitmanagement.queues.filter.Filter;
import com.smiler.rabbitmanagement.queues.filter.FilterDialog;
import com.smiler.rabbitmanagement.queues.sort.Sort;
import com.smiler.rabbitmanagement.queues.sort.SortDialog;
import com.smiler.rabbitmanagement.queues.sort.SortTypes;
import com.smiler.rabbitmanagement.settings.SettingsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.smiler.rabbitmanagement.Constants.STATE_FILTER_ID;
import static com.smiler.rabbitmanagement.Constants.STATE_SORT_ASC;
import static com.smiler.rabbitmanagement.Constants.STATE_SORT_TYPE;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        ProfileSelector.ProfileSelectorListener,
        FilterDialog.FilterDialogListener,
        SortDialog.OrderDialogListener,
        FragmentListListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    TextView drawerProfileTitle;
    PageType currentPageType = PageType.OVERVIEW;
    private boolean detailAdded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Snackbar.make(view, R.string.update_in_progress, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            updateData();
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        headerView.findViewById(R.id.nav_select_profile).setOnClickListener(v -> showProfileDialog());
        navigationView.setNavigationItemSelectedListener(this);
        drawerProfileTitle = headerView.findViewById(R.id.nav_profile);
        restoreState();
        showFragment(currentPageType);
    }

    private void restoreState() {
        int profileId = Profile.getSavedId(this);
        if (profileId != -1) {
            Profile profile = AppRepository.getInstance(getApplicationContext()).getProfile(profileId);
            setProfile(profile);
            if (!profile.getTitle().isEmpty()) {
                setDrawerProfile(profile);
            }
        }
        SharedPreferences statePref = getPreferences(Context.MODE_PRIVATE);
        int filterId = statePref.getInt(STATE_FILTER_ID, -1);
        String sortTypeValue = statePref.getString(STATE_SORT_TYPE, "");
        boolean sortAsc = statePref.getBoolean(STATE_SORT_ASC, false);
        if (filterId != -1) {
            Filter filter = AppRepository.getInstance(getApplicationContext()).getFilter(filterId);
            if (filter != null) {
                ViewModelProviders.of(this).get(QueuesListViewModel.class).setFilter(filter);
            }
        }
        if (!sortTypeValue.isEmpty()) {
            SortTypes sortType = null;
            try {
                sortType = SortTypes.valueOf(sortTypeValue);
            } catch (IllegalArgumentException e) {
                statePref.edit().remove(STATE_SORT_TYPE).apply();
                return;
            }
            Sort sort = new Sort().setAscending(sortAsc).setType(sortType);
            ViewModelProviders.of(this).get(QueuesListViewModel.class).setSort(sort);
        }
    }

    private void saveCurrentFilter(Filter filter) {
        getPreferences(Context.MODE_PRIVATE).edit().putInt(STATE_FILTER_ID, filter.getId()).apply();
    }

    private void saveCurrentSort(Sort sort) {
        getPreferences(Context.MODE_PRIVATE)
                .edit()
                .putString(STATE_SORT_TYPE, sort.getType().name())
                .putBoolean(STATE_SORT_ASC, sort.getAscending())
                .apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setToolbarTitle(currentPageType);
    }

    private void setDrawerProfile(Profile profile) {
        drawerProfileTitle.setText(String.format(getString(R.string.current_profile), profile.getTitle()));
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (detailAdded) {
                setToolbarTitle(currentPageType);
                detailAdded = false;
            }
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        switch (currentPageType) {
            case QUEUES:
                getMenuInflater().inflate(R.menu.queues, menu);
                break;
            default:
                getMenuInflater().inflate(R.menu.main, menu);
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                runSettingsActivity();
                return true;
            case R.id.action_filter:
                showFilterDialog();
                return true;
            case R.id.action_sort:
                showSortDialog();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showFragment(PageType type) {
        Fragment fragment = null;
        String tag = "";
        int titleRes = R.string.app_name;
        switch (type) {
            case OVERVIEW:
                fragment = OverviewFragment.newInstance();
                tag = OverviewFragment.TAG;
                titleRes = R.string.overview;
                break;
            case QUEUES:
                fragment = QueuesRecyclerFragment.newInstance();
                tag = QueuesRecyclerFragment.TAG;
                titleRes = R.string.queues;
                break;
            case CONNECTIONS:
                fragment = ConnectionsRecyclerFragment.newInstance();
                tag = ConnectionsRecyclerFragment.TAG;
                titleRes = R.string.connections;
                break;
            case CHANNELS:
                fragment = ChannelsRecyclerFragment.newInstance();
                tag = ChannelsRecyclerFragment.TAG;
                titleRes = R.string.channels;
                break;

        }
        ((UpdatableFragment) fragment).setListener(this);
        fragment.setRetainInstance(true);
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_layout_place, fragment, tag)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
        currentPageType = type;
        toolbar.setTitle(titleRes);
        invalidateOptionsMenu();
    }

    private void setToolbarTitle(PageType type) {
        int titleRes = R.string.app_name;
        switch (type) {
            case OVERVIEW:
                titleRes = R.string.overview;
                break;
            case QUEUES:
                titleRes = R.string.queues;
                break;
            case CONNECTIONS:
                titleRes = R.string.connections;
                break;
            case CHANNELS:
                titleRes = R.string.channels;
                break;
        }
        toolbar.setTitle(titleRes);
    }

    private void showDetails(PageType type, Object data) {
        DetailFragment fragment = null;
        String tag = "";

        switch (type) {
            case QUEUES:
                fragment = QueueDetailFragment.newInstance();
                tag = QueueDetailFragment.TAG;
                break;
            case CONNECTIONS:
                fragment = ConnectionDetailFragment.newInstance();
                tag = ConnectionDetailFragment.TAG;
                break;
            case CHANNELS:
                fragment = ChannelDetailFragment.newInstance();
                tag = ChannelDetailFragment.TAG;
                break;
        }
        if (fragment == null) {
            return;
        }

        fragment.setData(data);
        fragment.setRetainInstance(true);
//        fragment.setListener(this);
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(tag)
                .add(R.id.fragment_layout_place, fragment, tag)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
        detailAdded = true;
//        invalidateOptionsMenu();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        PageType type = null;
        switch (item.getItemId()) {
            case R.id.nav_overview:
                type = PageType.OVERVIEW;
                break;
            case R.id.nav_queues:
                type = PageType.QUEUES;
                break;
            case R.id.nav_connections:
                type = PageType.CONNECTIONS;
                break;
            case R.id.nav_channels:
                type = PageType.CHANNELS;
                break;
            case R.id.nav_settings:
                runSettingsActivity();
                break;
        }
        if (type != null) {
            showFragment(type);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void runSettingsActivity() {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    private void showProfileDialog() {
        ProfileSelector.newInstance().setListener(this).show(getFragmentManager(), ProfileSelector.TAG);
    }

    private void showFilterDialog() {
        FilterDialog.newInstance().setListener(this).show(getFragmentManager(), FilterDialog.TAG);
    }

    private void showSortDialog() {
        SortDialog.newInstance()
                .setListener(this)
                .setSort(ViewModelProviders.of(this).get(QueuesListViewModel.class).getSort())
                .show(getFragmentManager(), SortDialog.TAG);
    }

    private void updateData() {
        FragmentManager fm = getSupportFragmentManager();
        try {
            Fragment fragment = fm.findFragmentById(R.id.fragment_layout_place);
            if (fragment != null) {
                ((UpdatableFragment) fragment).updateData();
            }
        } catch (Exception e) {
//            log
        }
    }

    private void setProfile(Profile profile) {
        ((ManagementApplication) getApplicationContext()).setProfile(profile);
    }

    @Override
    public void onProfileSelected(Profile profile, boolean save, boolean saveCredentials) {
        setProfile(profile);
        setDrawerProfile(profile);
        if (save) {
            AppRepository.getInstance(getApplicationContext()).insertProfile(profile);
        }
        profile.saveCurrent(this);
    }

    @Override
    public void onFilterSelected(Filter filter, boolean saveForProfile) {
        FragmentManager fm = getSupportFragmentManager();
        try {
            Fragment fragment = fm.findFragmentById(R.id.fragment_layout_place);
            if (fragment != null) {
                ((QueuesRecyclerFragment) fragment).setQueuesFilter(filter, saveForProfile);
            }
        } catch (Exception e) {
            // log
        }
        saveCurrentFilter(filter);
    }

    @Override
    public void onListElementClick(PageType type, Object data) {
        showDetails(type, data);
    }

    @Override
    public void onOrderSelected(Sort sort) {
        FragmentManager fm = getSupportFragmentManager();
        try {
            Fragment fragment = fm.findFragmentById(R.id.fragment_layout_place);
            if (fragment != null) {
                ((QueuesRecyclerFragment) fragment).setQueuesOrder(sort);
            }
        } catch (Exception e) {
            // log
        }
        saveCurrentSort(sort);
    }
}
