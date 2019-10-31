package com.smiler.rabbitadministration.profiles;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.smiler.rabbitadministration.R;

import androidx.annotation.Nullable;
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

    @BindView(R.id.profile_edit_form)
    ProfileFormView profileFormView;
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
        View v = inflater.inflate(R.layout.profile_edit_dialog, null);
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
                                profileFormView.getFilledProfile(),
                                true,
                                saveCredentials.isChecked());
                    }
                })
                .setNegativeButton(R.string.apply, (dialog, which) -> {
                    if (listener != null) {
                        listener.onProfileSelected(
                                profileFormView.getFilledProfile(),
                                false,
                                saveCredentials.isChecked());
                    }
                })
                .setNeutralButton(R.string.cancel, (dialog, which) -> dismiss())
                .create();
    }

    private void setSelectedProfile(Profile profile) {
        profileFormView.setProfile(profile);
        selectedProfile = profile;
        profileSelectorApply.setText(String.format(getString(R.string.apply_selected), profile.getTitle()));
        profileSelectorApply.setVisibility(View.VISIBLE);
    }
}