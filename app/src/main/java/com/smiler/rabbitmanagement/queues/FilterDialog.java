package com.smiler.rabbitmanagement.queues;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.smiler.rabbitmanagement.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Setter;
import lombok.experimental.Accessors;


public class FilterDialog extends DialogFragment {
    public static String TAG = "RMQ-FilterDialog";
    private static final String ARG_VALUE = "value";
    private static final String ARG_REGEX = "regex";

    @Accessors(chain = true) @Setter
    private FilterDialogListener listener;

    @BindView(R.id.filter_value)
    EditText valueEditor;
    @BindView(R.id.filter_use_regex)
    CheckBox useRegex;
    @BindView(R.id.filter_save)
    CheckBox save;

    public interface FilterDialogListener {
        void onFilterSelected(String value, boolean regex, boolean saveForProfile);
    }

    public static FilterDialog newInstance() {
        return new FilterDialog();
    }

    public static FilterDialog newInstance(String value, boolean regex) {
        Bundle args = new Bundle();
        args.putString(ARG_VALUE, value);
        args.putBoolean(ARG_REGEX, regex);
        FilterDialog f = new FilterDialog();
        f.setArguments(args);
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle args = getArguments();
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.filter_dialog, null);
        ButterKnife.bind(this, v);

        builder.setView(v).setCancelable(true);

        if (args != null) {
            String value = args.getString(ARG_VALUE);
            if (value != null) {
                valueEditor.setText(value);
            }
            useRegex.setChecked(args.getBoolean(ARG_REGEX));
        }
        builder
                .setPositiveButton(R.string.action_apply, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null) {
                            listener.onFilterSelected(valueEditor.getText().toString(), useRegex.isChecked(), save.isChecked());
                        }
                    }
                })
                .setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
        return builder.create();
    }
}