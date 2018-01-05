package com.smiler.rabbitadministration.profiles;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.smiler.rabbitadministration.AppRepository;
import com.smiler.rabbitadministration.R;


public class ProfilesActivity extends AppCompatActivity implements
        ProfilesListFragment.ProfilesListListener
{
    private Menu menu;
    private boolean wide;
    private ProfileViewFragment detailViewFrag;
    private Toolbar toolbar;
    private Profile selectedProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles);
        initFragments();
        initToolbar();
    }

    protected void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        if (bar != null) {
            bar.setDisplayShowHomeEnabled(true);
            bar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void initFragments() {
        ProfilesListFragment fragment = new ProfilesListFragment();
        fragment.setListener(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.list_fragment, fragment, ProfilesListFragment.TAG)
                .commit();

        View view = findViewById(R.id.details_fragment);
        wide = view != null;
    }

    private void initDetailsFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        detailViewFrag = ProfileViewFragment.newInstance();
        transaction.replace(R.id.details_fragment, detailViewFrag, ProfileViewFragment.TAG);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0 ){
            getSupportFragmentManager().popBackStack();
            toolbar.setTitle(R.string.profiles);
            menu.setGroupVisible(R.id.profile_edit_group, false);
        } else if (detailViewFrag != null){
            emptyDetails();
        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.profiles, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                menuAdd();
                return true;
            case R.id.action_delete:
                menuDelete();
                return true;
            case R.id.action_save:
                menuSave();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void menuAdd() {
        selectedProfile = null;
        if (wide) {
            if (detailViewFrag == null) {
                initDetailsFragment();
            } else {
                detailViewFrag.clear();
            }
        } else {
            detailViewFrag = ProfileViewFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .addToBackStack(null)
                    .add(R.id.list_fragment, detailViewFrag, ProfileViewFragment.TAG)
                    .setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }
        menu.setGroupVisible(R.id.profile_edit_group, true);
    }

    private void menuSave() {
        if (detailViewFrag == null) {
            Toast.makeText(this, getString(R.string.error_occurred), Toast.LENGTH_SHORT).show();
            return;
        }
        Profile profile = detailViewFrag.getProfile();
        if (!profile.check()) {
            Toast.makeText(this, getString(R.string.profile_save_error), Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedProfile == null) {
            profile = AppRepository.getInstance(getApplicationContext()).insertProfile(profile);
            Toast.makeText(this, String.format(getString(R.string.create_success), profile.getTitle()), Toast.LENGTH_SHORT).show();
        } else {
            AppRepository.getInstance(getApplicationContext()).updateProfile(profile);
            Toast.makeText(this, String.format(getString(R.string.update_success), profile.getTitle()), Toast.LENGTH_SHORT).show();
        }
        refreshList();

        if (detailViewFrag != null) {
            selectedProfile = profile;
            detailViewFrag.updateContent(profile);
        }
    }

    private void menuDelete() {
        if (selectedProfile == null) {
            return;
        }
        try {
            AppRepository.getInstance(getApplicationContext()).deleteProfile(selectedProfile);
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.error_occurred), Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, String.format(getString(R.string.delete_success), selectedProfile.getTitle()), Toast.LENGTH_SHORT).show();
        emptyDetails();
        refreshList();
        selectedProfile = null;
    }

    private void refreshList() {
        ProfilesListFragment list = (ProfilesListFragment) getSupportFragmentManager().findFragmentByTag(ProfilesListFragment.TAG);
        if (list == null) { return; }
        list.refresh();
    }

    private void emptyDetails() {
        if (detailViewFrag == null) { return; }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.remove(detailViewFrag);
        transaction.commit();
        detailViewFrag = null;
        menu.setGroupVisible(R.id.profile_edit_group, false);
    }

    @Override
    public void onListElementClick(Profile profile) {
        selectedProfile = profile;
        if (wide) {
            if (detailViewFrag == null) {
                initDetailsFragment();
            }
            detailViewFrag.updateContent(profile);
        } else {
            detailViewFrag = ProfileViewFragment.newInstance();
            detailViewFrag.setProfile(profile);
            getSupportFragmentManager().beginTransaction()
                    .addToBackStack(null)
                    .add(R.id.list_fragment, detailViewFrag, ProfileViewFragment.TAG)
                    .setTransition(android.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit();
        }
        menu.setGroupVisible(R.id.profile_edit_group, true);
    }
}