package com.smiler.rabbitadministration.queues.filter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.smiler.rabbitadministration.R;
import com.smiler.rabbitadministration.profiles.ProfilesListDialog;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Setter;
import lombok.experimental.Accessors;


public class FilterDialog extends DialogFragment {
    public static String TAG = "RMQ-SortDialog";
    private static final String ARG_VALUE = "value";
    private static final String ARG_REGEX = "regex";

    @Accessors(chain = true) @Setter @Nullable
    private FilterDialogListener listener;

    @BindView(R.id.filter_value)
    EditText valueEditor;
    @BindView(R.id.filter_use_regex)
    CheckBox useRegex;
    @BindView(R.id.filter_save)
    CheckBox save;
    @BindView(R.id.filter_select)
    Button select;

    @Setter @Nullable
    private Filter filter;
    private Filter selectedFilter;

    public interface FilterDialogListener {
        void onFilterSelected(Filter filter, boolean saveForProfile);
    }

    public static FilterDialog newInstance() {
        return new FilterDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_filter, null);
        ButterKnife.bind(this, v);
        select.setOnClickListener(v1 -> FiltersListDialog.newInstance()
                .setListener(this::setSelectedFilter)
                .show(getFragmentManager(), ProfilesListDialog.TAG));

        if (filter != null) {
            valueEditor.setText(filter.getValue());
            useRegex.setChecked(filter.isRegex());
        }

        return new AlertDialog.Builder(getActivity())
                .setView(v).setCancelable(true)
                .setPositiveButton(R.string.apply, (dialog, which) -> {
                    if (listener != null) {
                        listener.onFilterSelected(
                                new Filter().setValue(valueEditor.getText().toString()).setRegex(useRegex.isChecked()),
                                save.isChecked());
                    }
                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> dismiss())
                .create();
    }

    private void setSelectedFilter(Filter filter) {
        selectedFilter = filter;
        valueEditor.setText(filter.getValue());
        useRegex.setChecked(filter.isRegex());
    }
}