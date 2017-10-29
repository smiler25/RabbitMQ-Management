package com.smiler.rabbitmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.smiler.rabbitmanagement.detail.QueueDetailFragment;
import com.smiler.rabbitmanagement.overview.OverviewFragment;
import com.smiler.rabbitmanagement.profiles.Profile;
import com.smiler.rabbitmanagement.profiles.ProfileSelector;
import com.smiler.rabbitmanagement.queues.QueuesRecyclerFragment;
import com.smiler.rabbitmanagement.settings.SettingsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        ProfileSelector.ProfileSelectorListener {

    private SectionsPagerAdapter sectionsPagerAdapter;
    @BindView(R.id.container)
    ViewPager viewPager;
    private QueueDetailFragment queueDetailFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, R.string.update_in_progress, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                updateData();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            runSettingsActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_camera:
                // Handle the camera action
                break;
            case R.id.nav_settings:
                runSettingsActivity();
                break;
            case R.id.nav_send:

                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void runSettingsActivity() {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    private void showProfileDialog() {
        ProfileSelector.newInstance().setListener(this).show(getFragmentManager(), ProfileSelector.TAG);
    }

    private void updateData() {
        FragmentManager fm = getSupportFragmentManager();
        try {
            Fragment overview = fm.findFragmentByTag(sectionsPagerAdapter.getFragmentTag(viewPager.getId(), SectionsPagerAdapter.POSITION_OVERVIEW));
            if (overview != null) {
                ((OverviewFragment) overview).requestData();
            }
        } catch (Exception e) {
//            log
        }
        try {
            Fragment list = fm.findFragmentByTag(sectionsPagerAdapter.getFragmentTag(viewPager.getId(), SectionsPagerAdapter.POSITION_QUEUES));
            if (list != null) {
                ((QueuesRecyclerFragment) list).requestData();
            }
        } catch (Exception e) {
//            log
        }
    }

    //    private void initFragments() {
//        TeamsRecyclerFragment fragment = new TeamsRecyclerFragment();
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//
//        transaction.replace(R.id.recycler_content_fragment, fragment, TeamsRecyclerFragment.TAG);
//
//        View view = findViewById(R.id.details_frag);
//        if (view != null) {
//            wide = true;
//            queueDetailFrag = new QueueDetailFragment();
//            transaction.replace(R.id.details_frag, queueDetailFrag, QueueDetailFragment.TAG);
//        } else {
//            wide = false;
//            queueDetailFrag = null;
//        }
//        transaction.commit();
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (getSupportFragmentManager().getBackStackEntryCount() > 0 ){
//            getSupportFragmentManager().popBackStack();
//            toolbar.setTitle(R.string.title_activity_teams);
//        } else {
//            finish();
//        }
//    }

    private void openQueueDetails() {
//        String name = "";
//        if (queueDetailFrag != null && queueDetailFrag.isAdded()) {
//            queueDetailFrag.updateContent(name);
//
//        } else {
//            Fragment selectedFrag = QueueDetailFragment.newInstance(name);
//            getSupportFragmentManager().beginTransaction()
//                    .addToBackStack(null)
//                    .add(R.id.recycler_content_fragment, selectedFrag, QueueDetailFragment.TAG)
//                    .setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                    .commit();
//        }
    }

    @Override
    public void onProfileSelected(String title) {
//        currentProfile = Profile();
    }

    @Override
    public void onProfileCreated(String title, String host, String login, String password) {
        ((ManagementApplication) getApplicationContext()).setProfile(new Profile(title, host, login, password));
    }

    @Override
    public void onProfileCreatedSave(String title, String host, String login, String password) {
        ((ManagementApplication) getApplicationContext()).setProfile(new Profile(title, host, login, password).save(this));
    }
}
