package com.smiler.rabbitadministration.profiles;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smiler.rabbitadministration.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileView extends LinearLayout {
    public static String TAG = "BS-ProfileView";

    @BindView(R.id.profile_view_title)
    TextView title;
    @BindView(R.id.profile_edit_form)
    ProfileFormView profileFormView;

    public ProfileView(Context context, Profile profile) {
        super(context);
        if(!isInEditMode()) {
            init(profile);
        }
    }

    private void init(Profile profile) {
        View v = inflate(getContext(), R.layout.profile_view, this);
        ButterKnife.bind(this, v);

        title = findViewById(R.id.profile_view_title);
        if (profile == null) {
            return;
        }
        title.setText(profile.getTitle());
        profileFormView.setProfile(profile);
    }

    public void setProfile(Profile value) {
        title.setText(value.getTitle());
        profileFormView.setProfile(value);
    }

    public void clear() {
        title.setText("");
        profileFormView.clear();
    }

    public Profile getProfile() {
        return profileFormView.getFilledProfile();
    }

    public void setListener() {

    }
}