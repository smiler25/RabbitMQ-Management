package com.smiler.rabbitadministration.profiles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import lombok.Setter;


public class ProfileViewFragment extends Fragment {
    public static String TAG = "BS-ProfileViewFragment";
    @Setter
    private Profile profile;
    private ProfileView view;

    public static ProfileViewFragment newInstance() {
        return new ProfileViewFragment();
    }

    public ProfileViewFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = new ProfileView(getActivity(), profile);
        return view;
    }

    public void updateContent(Profile profile) {
        this.profile = profile;
        if (view != null) {
            view.setProfile(profile);
        }
    }

    public void clear() {
        this.profile = null;
        if (view != null) {
            view.clear();
        }
    }

    public Profile getProfile() {
        if (view == null) {
            return null;
        }
        Profile editedProfile = view.getProfile();
        if (profile != null) {
            editedProfile.setId(profile.getId());
        }
        profile = editedProfile;
        return profile;
    }
}