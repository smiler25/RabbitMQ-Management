package com.smiler.rabbitadministration.queues.sort;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.smiler.rabbitadministration.R;

import java.util.Map;
import java.util.TreeMap;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Setter;
import lombok.experimental.Accessors;


public class SortDialog extends DialogFragment {
    public static String TAG = "RMQ-SortDialog";

    @Accessors(chain = true) @Setter @Nullable
    private OrderDialogListener listener;

    @Accessors(chain = true) @Setter @Nullable
    private Sort sort;

    @BindView(R.id.sort_parameters)
    ViewGroup sortParameters;

    @BindView(R.id.sort_ascending_group)
    RadioGroup ascGroup;

    @BindView(R.id.sort_type_group)
    RadioGroup typeGroup;
    @BindView(R.id.sort_enabled)
    CheckBox enabledCheckBox;

    public interface OrderDialogListener {
        void onOrderSelected(Sort sort);
    }

    public static SortDialog newInstance() {
        return new SortDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_sort, null);
        ButterKnife.bind(this, v);

        initAscGroup();
        initTypeGroup(getActivity());
        setState();

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setCancelable(true)
                .setPositiveButton(R.string.apply, (dialog, which) -> {
                    if (listener != null) {
                        listener.onOrderSelected(getSelectedSort());
                    }
                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> dismiss())
                .create();
    }

    private void setState() {
        if (sort == null || sort.getType() == SortTypes.NONE) {
            enabledCheckBox.setChecked(false);
            sortParameters.setVisibility(View.GONE);
        } else {
            enabledCheckBox.setChecked(true);
            sortParameters.setVisibility(View.VISIBLE);
        }
        enabledCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                sortParameters.setVisibility(View.VISIBLE);
            } else {
                sortParameters.setVisibility(View.GONE);
            }
        });
    }

    private void initAscGroup() {
        if (sort == null || sort.getAscending()) {
            ((RadioButton) ascGroup.findViewById(R.id.sort_asc)).setChecked(true);
        } else {
            ((RadioButton) ascGroup.findViewById(R.id.sort_desc)).setChecked(true);
        }
    }

    private void initTypeGroup(Context context) {
        TreeMap<Integer, SortTypes> values = new TreeMap<Integer, SortTypes>() {{
            put(R.string.sort_by_name, SortTypes.NAME);
            put(R.string.sort_by_ready, SortTypes.READY);
            put(R.string.sort_by_unacked, SortTypes.UNACKED);
            put(R.string.sort_by_total, SortTypes.TOTAL);
        }};

        SortTypes selectedType = (sort == null) ? SortTypes.NONE : sort.getType();

        for (Map.Entry<Integer, SortTypes> item : values.entrySet()) {
            RadioButton bu = new RadioButton(context);
            bu.setText(item.getKey());
            bu.setTag(item.getValue());
            typeGroup.addView(bu);
            if (item.getValue() == selectedType) {
                typeGroup.check(bu.getId());
            }
        }
    }

    private Sort getSelectedSort() {
        if (!enabledCheckBox.isChecked()) {
            return new Sort().setType(SortTypes.NONE);
        }

        RadioButton checkedRadioButton = typeGroup.findViewById(typeGroup.getCheckedRadioButtonId());
        return new Sort()
                .setAscending(((RadioButton) ascGroup.findViewById(R.id.sort_asc)).isChecked())
                .setType((SortTypes) checkedRadioButton.getTag());
    }
}