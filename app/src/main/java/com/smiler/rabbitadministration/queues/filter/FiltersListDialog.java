package com.smiler.rabbitadministration.queues.filter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.smiler.rabbitadministration.AppRepository;

import java.util.List;

import androidx.annotation.Nullable;
import lombok.Setter;
import lombok.experimental.Accessors;


public class FiltersListDialog extends DialogFragment {
    public static final String TAG = "RMQ-FiltersListDialog";

    @Accessors(chain = true) @Setter @Nullable
    private ListDialogListener listener;

    public interface ListDialogListener {
        void onSelectFilter(Filter filter);
    }

    public static FiltersListDialog newInstance() {
        return new FiltersListDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        List<Filter> filters = AppRepository.getInstance(getActivity().getApplicationContext()).getAllFiltersSync();
        FiltersListAdapter adapter = new FiltersListAdapter(getActivity(), filters);

        return new AlertDialog.Builder(getActivity())
                .setAdapter(adapter, itemSelectListener)
                .setCancelable(true)
                .create();
    }

    DialogInterface.OnClickListener itemSelectListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if (listener != null) {
                listener.onSelectFilter((Filter) ((AlertDialog) dialog).getListView().getAdapter().getItem(which));
            }
        }
    };
}