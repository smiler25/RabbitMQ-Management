package com.smiler.rabbitmanagement.profiles;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.smiler.rabbitmanagement.AppRepository;

import java.util.List;

import lombok.Setter;
import lombok.experimental.Accessors;


public class ProfilesListDialog extends DialogFragment {
    public static final String TAG = "RMQ-ProfilesListDialog";

    @Accessors(chain = true) @Setter @Nullable
    private ListDialogListener listener;

    public interface ListDialogListener {
        void onSelectProfile(Profile team);
    }

    public static ProfilesListDialog newInstance() {
        return new ProfilesListDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        List<Profile> profiles = AppRepository.getInstance(getActivity().getApplicationContext()).getAllProfilesSync();
        ProfilesListAdapter adapter = new ProfilesListAdapter(getActivity(), profiles);

        return new AlertDialog.Builder(getActivity())
                .setAdapter(adapter, selectProfileListener)
                .setCancelable(true)
                .create();
    }

    DialogInterface.OnClickListener selectProfileListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (listener != null) {
                listener.onSelectProfile((Profile) ((AlertDialog) dialog).getListView().getAdapter().getItem(which));
            }
        }
    };
}