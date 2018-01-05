package com.smiler.rabbitadministration.common;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.smiler.rabbitadministration.R;

import lombok.Setter;
import lombok.experimental.Accessors;


public class ConfirmDialog extends DialogFragment implements DialogInterface.OnClickListener {
    private static String argType = "type";
    private ActionTypes type;
    @Setter @Accessors(chain = true)
    private String queueName;

    @Setter @Accessors(chain = true) @Nullable
    private ConfirmDialogListener listener;

    public static ConfirmDialog newInstance(ActionTypes type) {
        ConfirmDialog f = new ConfirmDialog();
        Bundle args = new Bundle();
        args.putSerializable(argType, type);
        f.setArguments(args);
        return f;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setPositiveButton(R.string.confirm, this)
                .setNegativeButton(R.string.cancel, this)
                .setTitle(R.string.confirm_action);

        Bundle args = getArguments();
        type = (ActionTypes) args.get(argType);

        if (type != null) {
            String msg = "";
            switch (type) {
                case QUEUE_DELETE:
                    msg = String.format(getResources().getString(R.string.confirm_delete_queue), queueName);
                    break;
                case QUEUE_PURGE:
                    msg = String.format(getResources().getString(R.string.confirm_purge_queue), queueName);
                    break;
            }
            if (!msg.equals("")) {
                builder.setMessage(msg);
            }
        }
        return builder.create();
    }

    public interface ConfirmDialogListener {
        void onConfirmDialogPositive(ActionTypes type);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (listener == null) {
            return;
        }
        if (which == Dialog.BUTTON_POSITIVE) {
            listener.onConfirmDialogPositive(type);
        }
    }
}