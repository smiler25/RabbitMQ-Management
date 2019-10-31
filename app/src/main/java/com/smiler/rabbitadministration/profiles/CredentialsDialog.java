package com.smiler.rabbitadministration.profiles;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.smiler.rabbitadministration.R;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Setter;
import lombok.experimental.Accessors;


public class CredentialsDialog extends DialogFragment {
    public static String TAG = "RMQ-CredentialsDialog";

    @Setter @Accessors(chain = true)
    private Profile profile;
    @Setter @Accessors(chain = true) @Nullable
    private CredentialsDialogListener listener;

    @BindView(R.id.profile_login)
    EditText login;
    @BindView(R.id.profile_password)
    EditText password;


    public interface CredentialsDialogListener {
        void onCredentialsEntered(Profile profile);
    }

    public static CredentialsDialog newInstance() {
        return new CredentialsDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.credentials_form, null);
        ButterKnife.bind(this, v);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setCancelable(true)
                .setTitle(String.format(getString(R.string.credentials_form_title), profile.getTitle()))
                .setPositiveButton(R.string.apply, (dialog, which) -> {})
                .setNegativeButton(R.string.cancel, (dialog, which) -> dismiss())
                .create();
    }

    @Override
    public void onStart() {
        super.onStart();
        AlertDialog d = (AlertDialog)getDialog();
        Button positiveButton = d.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(v1 -> {
            if (listener != null) {
                String loginValue = login.getText().toString();
                String passwordValue = password.getText().toString();
                if (loginValue.isEmpty() || passwordValue.isEmpty()) {
                    Toast.makeText(getActivity(), R.string.credentials_form_error,Toast.LENGTH_LONG).show();
                    return;
                }
                profile.setCredentials(loginValue, passwordValue);
                listener.onCredentialsEntered(profile);
                dismiss();
            }
        });
    }
}