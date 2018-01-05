package com.smiler.rabbitadministration.profiles;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.smiler.rabbitadministration.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ProfileFormView extends LinearLayout {
    @BindView(R.id.profile_title)
    EditText title;
    @BindView(R.id.profile_host)
    EditText host;
    @BindView(R.id.profile_login)
    EditText login;
    @BindView(R.id.profile_password)
    EditText password;

    public ProfileFormView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        View view = inflate(context, R.layout.profile_edit_form, this);
        ButterKnife.bind(this, view);
    }

    public Profile getFilledProfile() {
        return new Profile(title.getText().toString(), host.getText().toString(), login.getText().toString(), password.getText().toString());
    }

    public void setProfile(Profile profile) {
        title.setText(profile.getTitle());
        host.setText(profile.getHost());
        login.setText(profile.getLogin());
        if (profile.getPassword() != null) {
            password.setText(profile.getPassword());
        }
    }

    public void clear() {
        title.setText("");
        host.setText("");
        login.setText("");
        password.setText("");
    }
}