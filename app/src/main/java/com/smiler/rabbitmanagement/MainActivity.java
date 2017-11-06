package com.smiler.rabbitmanagement;

import android.content.Intent;
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

import com.smiler.rabbitmanagement.channels.ChannelsRecyclerFragment;
import com.smiler.rabbitmanagement.connections.ConnectionsRecyclerFragment;
import com.smiler.rabbitmanagement.detail.QueueDetailFragment;
import com.smiler.rabbitmanagement.overview.OverviewFragment;
import com.smiler.rabbitmanagement.profiles.Profile;
import com.smiler.rabbitmanagement.profiles.ProfileSelector;
import com.smiler.rabbitmanagement.queues.FilterDialog;
import com.smiler.rabbitmanagement.queues.QueuesRecyclerFragment;
import com.smiler.rabbitmanagement.settings.SettingsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        ProfileSelector.ProfileSelectorListener, FilterDialog.FilterDialogListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    TextView drawerProfileTitle;

    private QueueDetailFragment queueDetailFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Profile profile = Profile.getProfile(this);
        setProfile(profile);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.update_in_progress, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                updateData();
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        headerView.findViewById(R.id.nav_select_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProfileDialog();
            }
        });
        drawerProfileTitle = headerView.findViewById(R.id.nav_profile);
        if (profile.getTitle() != null && !profile.getTitle().equals("")) {
            setDrawerProfile(profile);
        }

        navigationView.setNavigationItemSelectedListener(this);
        showFragment(PageType.OVERVIEW);
    }

    private void setDrawerProfile(Profile profile) {
        drawerProfileTitle.setText(String.format(getString(R.string.current_profile), profile.getTitle()));
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
        fragment.setRetainInstance(true);
        // fragment.setListener(this);
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_layout_place, fragment, tag)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
        toolbar.setTitle(titleRes);
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

    private void updateData() {
        FragmentManager fm = getSupportFragmentManager();
//        try {
//            Fragment fragment = fm.findFragmentById(R.id.container_common);
//            if (fragment != null) {
//                ((UpdatableFragment) fragment).updateData();
//            }
//        } catch (Exception e) {
////            log
//        }
    }

//    @Override
//    public void onBackPressed() {
//        if (getSupportFragmentManager().getBackStackEntryCount() > 0 ){
//            getSupportFragmentManager().popBackStack();
//            toolbar.setTitle(R.string.title_activity_teams);
//        } else {
//            finish();
//        }
//    }

    @Override
    public void onProfileSelected(String title) {
//        currentProfile = Profile();
    }

    private void setProfile(Profile profile) {
        ((ManagementApplication) getApplicationContext()).setProfile(profile);
    }

    @Override
    public void onProfileCreated(String title, String host, String login, String password) {
        Profile profile = new Profile(title, host, login, password);
        setProfile(profile);
        setDrawerProfile(profile);
    }

    @Override
    public void onProfileCreatedSave(String title, String host, String login, String password) {
        Profile profile = new Profile(title, host, login, password).save(this);
        setProfile(profile);
        setDrawerProfile(profile);
    }

    @Override
    public void onFilterSelected(String value, boolean regex, boolean saveForProfile) {

    }
}
