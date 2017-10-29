package com.smiler.rabbitmanagement.profiles;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.smiler.rabbitmanagement.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Setter;
import lombok.experimental.Accessors;


public class ProfileSelector extends DialogFragment {
    public static String TAG = "RMQ-ProfileSelector";

    @Accessors(chain = true) @Setter
    private ProfileSelectorListener listener;

    @BindView(R.id.edit_title)
    EditText title;
    @BindView(R.id.edit_host)
    EditText host;
    @BindView(R.id.edit_login)
    EditText login;
    @BindView(R.id.edit_password)
    EditText password;

//    @BindView(R.id.edit_title)
//    private Button profileSelector;

    public interface ProfileSelectorListener {
        void onProfileSelected(String title);
        void onProfileCreated(String title, String host, String login, String password);
        void onProfileCreatedSave(String title, String host, String login, String password);
    }

    public static ProfileSelector newInstance() {
        return new ProfileSelector();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.profile_edit, null);
        ButterKnife.bind(this, v);

        builder.setView(v).setCancelable(true);

//        TextView title = v.findViewById(R.id.dialog_title);
//        title.setText(String.format(getResources().getString(titleResId), teamType));

//        String name = args.getString("name");
//        if (name != null) {
//            host.setText(name);
//            host.selectAll();
//        }

        builder
                .setPositiveButton(R.string.action_apply_save, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null) {
                            listener.onProfileCreatedSave(title.getText().toString(), host.getText().toString(), login.getText().toString(), password.getText().toString());
                        }
                    }
                })
                .setNegativeButton(R.string.action_apply, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null) {
                            listener.onProfileCreated(title.getText().toString(), host.getText().toString(), login.getText().toString(), password.getText().toString());
                        }
                    }
                })
                .setNeutralButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
        return builder.create();
    }
}