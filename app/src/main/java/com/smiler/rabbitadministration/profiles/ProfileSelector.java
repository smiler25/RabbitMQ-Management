package com.smiler.rabbitadministration.profiles;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.smiler.rabbitadministration.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Setter;
import lombok.experimental.Accessors;


public class ProfileSelector extends DialogFragment {
    public static String TAG = "RMQ-ProfileSelector";

    @Accessors(chain = true) @Setter @Nullable
    private Profile profile;

    private Profile selectedProfile;
    @Accessors(chain = true) @Setter @Nullable
    private ProfileSelectorListener listener;

    @BindView(R.id.profile_title)
    EditText title;
    @BindView(R.id.profile_host)
    EditText host;
    @BindView(R.id.profile_login)
    EditText login;
    @BindView(R.id.profile_password)
    EditText password;
    @BindView(R.id.profile_save_credentials)
    CheckBox saveCredentials;

    @BindView(R.id.profile_select)
    Button profileSelector;
    @BindView(R.id.profile_select_apply)
    Button profileSelectorApply;


    public interface ProfileSelectorListener {
        void onProfileSelected(Profile profile, boolean save, boolean saveCredentials);
    }

    public static ProfileSelector newInstance() {
        return new ProfileSelector();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.profile_edit, null);
        ButterKnife.bind(this, v);

        profileSelector.setOnClickListener(v1 -> ProfilesListDialog.newInstance()
                .setListener(this::setSelectedProfile)
                .show(getFragmentManager(), ProfilesListDialog.TAG));

        profileSelectorApply.setOnClickListener(v1 -> {
            if (listener != null) {
                if (selectedProfile != null) {
                    listener.onProfileSelected(selectedProfile, false, false);
                    dismiss();
                } else {
                    Toast.makeText(getActivity(), R.string.profile_not_selected, Toast.LENGTH_LONG).show();
                }
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setCancelable(true)
                .setPositiveButton(R.string.apply_save, (dialog, which) -> {
                    if (listener != null) {
                        listener.onProfileSelected(
                                new Profile(title.getText().toString(), host.getText().toString(), login.getText().toString(), password.getText().toString()),
                                true,
                                saveCredentials.isChecked());
                    }
                })
                .setNegativeButton(R.string.apply, (dialog, which) -> {
                    if (listener != null) {
                        listener.onProfileSelected(
                                new Profile(title.getText().toString(), host.getText().toString(), login.getText().toString(), password.getText().toString()),
                                false,
                                saveCredentials.isChecked());
                    }
                })
                .setNeutralButton(R.string.cancel, (dialog, which) -> dismiss())
                .create();
    }

    private void setSelectedProfile(Profile profile) {
        title.setText(profile.getTitle());
        host.setText(profile.getHost());
        login.setText(profile.getLogin());
        selectedProfile = profile;
        profileSelectorApply.setText(String.format(getString(R.string.apply_selected), profile.getTitle()));
        profileSelectorApply.setVisibility(View.VISIBLE);
    }
}