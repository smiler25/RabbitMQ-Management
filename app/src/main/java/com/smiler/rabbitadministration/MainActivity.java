package com.smiler.rabbitadministration;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
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
import android.widget.Toast;

import com.smiler.rabbitadministration.base.AndroidUtils;
import com.smiler.rabbitadministration.base.DetailFragment;
import com.smiler.rabbitadministration.base.interfaces.FragmentListListener;
import com.smiler.rabbitadministration.base.interfaces.UpdatableFragment;
import com.smiler.rabbitadministration.base.interfaces.UpdatableFragmentListener;
import com.smiler.rabbitadministration.channels.ChannelDetailFragment;
import com.smiler.rabbitadministration.channels.ChannelsRecyclerFragment;
import com.smiler.rabbitadministration.common.ActionInfo;
import com.smiler.rabbitadministration.common.ActionTypes;
import com.smiler.rabbitadministration.common.ConfirmDialog;
import com.smiler.rabbitadministration.connections.ConnectionDetailFragment;
import com.smiler.rabbitadministration.connections.ConnectionsRecyclerFragment;
import com.smiler.rabbitadministration.detail.QueueDetailFragment;
import com.smiler.rabbitadministration.info.PolicyActivity;
import com.smiler.rabbitadministration.overview.OverviewFragment;
import com.smiler.rabbitadministration.preferences.PrefActivity;
import com.smiler.rabbitadministration.preferences.Preferences;
import com.smiler.rabbitadministration.profiles.Profile;
import com.smiler.rabbitadministration.profiles.ProfileSelector;
import com.smiler.rabbitadministration.profiles.ProfilesActivity;
import com.smiler.rabbitadministration.queues.QueuesListViewModel;
import com.smiler.rabbitadministration.queues.QueuesRecyclerFragment;
import com.smiler.rabbitadministration.queues.filter.Filter;
import com.smiler.rabbitadministration.queues.filter.FilterDialog;
import com.smiler.rabbitadministration.queues.sort.Sort;
import com.smiler.rabbitadministration.queues.sort.SortDialog;
import com.smiler.rabbitadministration.queues.sort.SortTypes;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.smiler.rabbitadministration.Constants.STATE_CURRENT_PAGE;
import static com.smiler.rabbitadministration.Constants.STATE_FILTER_ID;
import static com.smiler.rabbitadministration.Constants.STATE_SORT_ASC;
import static com.smiler.rabbitadministration.Constants.STATE_SORT_TYPE;
import static com.smiler.rabbitadministration.Constants.TAG_FRAGMENT_CONFIRM;

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        ProfileSelector.ProfileSelectorListener,
        ConfirmDialog.ConfirmDialogListener,
        FilterDialog.FilterDialogListener,
        SortDialog.OrderDialogListener,
        FragmentListListener,
        UpdatableFragmentListener
{

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.loader_overlay)
    View loaderOverlay;

    private Preferences preferences;
    private TextView drawerProfileTitle;
    private PageType currentPageType = PageType.OVERVIEW;
    private boolean detailAdded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> updateData());

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        headerView.findViewById(R.id.nav_select_profile).setOnClickListener(v -> showProfileDialog());
        navigationView.setNavigationItemSelectedListener(this);
        drawerProfileTitle = headerView.findViewById(R.id.nav_profile);
        preferences = Preferences.getInstance(getApplicationContext());
        preferences.read();
        preferences.setAfterCreate(true);
        restoreState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setToolbarTitle(currentPageType);
        if (preferences.isAfterCreate()) {
            preferences.setAfterCreate(false);
        } else {
            preferences.read();
            if (preferences.isSaveActiveProfileChanged()) {
                Profile profile = getProfile();
                if (profile == null) {
                    restoreProfile();
                } else {
                    profile.saveCurrent(this);
                }
            }
            if (preferences.isSaveActiveFilterChanged()) {
                restoreFilter();
            }
            if (preferences.isSaveActiveSortChanged()) {
                restoreSort();
            }
        }
        preferences.resetChangeStates();
        showFragment(currentPageType);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable(STATE_CURRENT_PAGE, currentPageType);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        currentPageType = (PageType) savedInstanceState.getSerializable(STATE_CURRENT_PAGE);
    }

    private void restoreState() {
        if (preferences.isSaveActiveProfile()) {
            restoreProfile();
        }
        if (preferences.isSaveActiveFilter()) {
            restoreFilter();
        }
        if (preferences.isSaveActiveSort()) {
            restoreSort();
        }
    }

    private void restoreProfile() {
        int profileId = Profile.getSavedId(this);
        if (profileId != -1) {
            Profile profile = AppRepository.getInstance(getApplicationContext()).getProfile(profileId);
            if (profile != null) {
                setProfile(profile);
                if (!profile.getTitle().isEmpty()) {
                    setDrawerProfile(profile);
                }
            }
        }
    }

    private void restoreFilter() {
        SharedPreferences statePref = getPreferences(Context.MODE_PRIVATE);
        int filterId = statePref.getInt(STATE_FILTER_ID, -1);
        if (filterId != -1) {
            Filter filter = AppRepository.getInstance(getApplicationContext()).getFilter(filterId);
            if (filter != null) {
                ViewModelProviders.of(this).get(QueuesListViewModel.class).setFilter(filter);
            }
        }
    }

    private void restoreSort() {
        SharedPreferences statePref = getPreferences(Context.MODE_PRIVATE);
        String sortTypeValue = statePref.getString(STATE_SORT_TYPE, "");
        boolean sortAsc = statePref.getBoolean(STATE_SORT_ASC, false);
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

    private void setDrawerProfile(Profile profile) {
        drawerProfileTitle.setText(String.format(getString(R.string.current_profile), profile.getTitle()));
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (detailAdded) {
                switch (currentPageType) {
                    case QUEUE_DETAIL:
                        currentPageType = PageType.QUEUES;
                        break;
                    case CONNECTION_DETAIL:
                        currentPageType = PageType.CONNECTIONS;
                        break;
                    case CHANNEL_DETAIL:
                        currentPageType = PageType.CHANNELS;
                        break;
                }
                setToolbarTitle(currentPageType);
                invalidateOptionsMenu();
                detailAdded = false;
            }
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        switch (currentPageType) {
            case QUEUES:
                getMenuInflater().inflate(R.menu.queues, menu);
                break;
            case QUEUE_DETAIL:
                getMenuInflater().inflate(R.menu.queue_detail, menu);
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
            case R.id.action_queue_purge:
                askActionQueue(ActionTypes.QUEUE_PURGE);
                return true;
            case R.id.action_queue_delete:
                askActionQueue(ActionTypes.QUEUE_DELETE);
                return true;
            case R.id.action_queue_move:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showFragment(PageType type) {
        currentPageType = type;
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
            case QUEUE_DETAIL:
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
        if (fragment == null) {
            return;
        }
        ((UpdatableFragment) fragment).setListener(this);
        ((UpdatableFragment) fragment).setCallback(this);
        fragment.setRetainInstance(true);
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_layout_place, fragment, tag)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
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
            case QUEUE_DETAIL:
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
        currentPageType = type;

        switch (type) {
            case QUEUE_DETAIL:
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

        fragment.setRetainInstance(true);
        fragment.setCallback(this);
//        fragment.setListener(this);
        getSupportFragmentManager().beginTransaction()
                .addToBackStack(tag)
                .add(R.id.fragment_layout_place, fragment, tag)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
        detailAdded = true;
        fragment.setData(data);
        invalidateOptionsMenu();
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
            case R.id.nav_profiles:
                runProfilesActivity();
                break;
            case R.id.nav_settings:
                runSettingsActivity();
                break;
            case R.id.nav_policy:
                runPolicyActivity();
                break;
        }
        if (type != null) {
            showFragment(type);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void runSettingsActivity() {
        startActivity(new Intent(this, PrefActivity.class));
    }

    private void runProfilesActivity() {
        startActivity(new Intent(this, ProfilesActivity.class));
    }

    private void runPolicyActivity() {
        startActivity(new Intent(this, PolicyActivity.class));
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
//                if (view != null) {
//                    Snackbar.make(view, R.string.update_in_progress, Snackbar.LENGTH_LONG).show();
//                }
            }
        } catch (Exception e) {
//            log
        }
    }

    private void showLoaderView() {
        AndroidUtils.animateView(loaderOverlay, View.VISIBLE, 0.4f, 200);
    }

    private void hideLoaderView() {
        AndroidUtils.animateView(loaderOverlay, View.GONE, 0, 200);
    }

    private void setProfile(Profile profile) {
        ((ManagementApplication) getApplicationContext()).setProfile(profile);
    }

    private Profile getProfile() {
        return ((ManagementApplication) getApplicationContext()).getProfile();
    }

    @Override
    public void onProfileSelected(Profile profile, boolean save, boolean saveCredentials) {
        profile.setStoreCredentials(saveCredentials);
        setProfile(profile);
        setDrawerProfile(profile);
        if (save) {
            profile = AppRepository.getInstance(getApplicationContext()).insertProfile(profile);
        }
        profile.saveCurrent(this);
    }

    @Override
    public void onFilterSelected(Filter filter, boolean saveForProfile) {
        FragmentManager fm = getSupportFragmentManager();
        try {
            Fragment fragment = fm.findFragmentById(R.id.fragment_layout_place);
            if (fragment != null) {
                filter = ((QueuesRecyclerFragment) fragment).setQueuesFilter(filter, saveForProfile);
            }
            saveCurrentFilter(filter);
        } catch (Exception e) {
            // log
        }
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

    @Override
    public void startLoading() {
        showLoaderView();
    }

    @Override
    public void stopLoading() {
        hideLoaderView();
    }

    @Override
    public void handleAction(ActionInfo actionInfo) {
        String msg = null;
        switch (actionInfo.getAction()) {
            case QUEUE_DELETE:
                if (detailAdded && currentPageType == PageType.QUEUE_DETAIL) {
                    getSupportFragmentManager().popBackStack();
                }
                showFragment(PageType.QUEUES);
                msg = String.format(getString(R.string.delete_success), actionInfo.getText());
                break;
            case QUEUE_PURGE:
                updateData();
                msg = String.format(getString(R.string.purge_success), actionInfo.getText());
                break;
            case QUEUE_MOVE:
                msg = String.format(getString(R.string.move_success), actionInfo.getText());
                break;
        }
        if (msg != null) {
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConfirmDialogPositive(ActionTypes type) {
        switch (type) {
            case QUEUE_DELETE:
                deleteQueue();
                break;
            case QUEUE_PURGE:
                purgeQueue();
                break;
            case QUEUE_MOVE:
                break;
        }
    }

    @Override
    public void onConfirmDialogNegative(ActionTypes type) {

    }

    private QueueDetailFragment getQueueDetailFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(QueueDetailFragment.TAG);
        if (fragment == null) {
            Toast.makeText(this, String.format(getString(R.string.api_error_queue_detail), ""), Toast.LENGTH_LONG).show();
            return null;
        }
        try {
            return (QueueDetailFragment) fragment;
        } catch (ClassCastException e) {
            Toast.makeText(this, String.format(getString(R.string.api_error_queue_detail), ""), Toast.LENGTH_LONG).show();
            return null;
        }
    }

    private void askActionQueue(ActionTypes type) {
        QueueDetailFragment fragment = getQueueDetailFragment();
        if (fragment == null) {
            return;
        }
        String qname = fragment.getQueueName();
        if (qname == null || qname.isEmpty()) {
            Toast.makeText(this, String.format(getString(R.string.api_error_queue_detail), "queue not defined"), Toast.LENGTH_LONG).show();
            return;
        }
        ConfirmDialog.newInstance(type).setListener(this).setQueueName(qname).show(getFragmentManager(), TAG_FRAGMENT_CONFIRM);
    }

    private void deleteQueue() {
        QueueDetailFragment fragment = getQueueDetailFragment();
        if (fragment == null) {
            return;
        }
        fragment.deleteQueue();
    }

    private void purgeQueue() {
        QueueDetailFragment fragment = getQueueDetailFragment();
        if (fragment == null) {
            return;
        }
        fragment.purgeQueue();
    }

    private void updateQueueInfo() {
        QueueDetailFragment fragment = getQueueDetailFragment();
        if (fragment == null) {
            return;
        }
        fragment.purgeQueue();
    }
}