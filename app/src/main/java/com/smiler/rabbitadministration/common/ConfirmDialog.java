package com.smiler.rabbitadministration.common;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.smiler.rabbitadministration.R;

import lombok.Setter;


public class ConfirmDialog extends DialogFragment implements DialogInterface.OnClickListener {
    private static String argType = "type";
    private DialogTypes type;

    @Setter
    private ConfirmDialogListener listener;

    public static ConfirmDialog newInstance(DialogTypes type) {
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
                .setNegativeButton(R.string.cancel, this);

        Bundle args = getArguments();
        type = (DialogTypes) args.get(argType);

        if (type != null) {
            int titleId = R.string.confirm;
            String title = "";
            String msg = "";
            switch (type) {
                case DELETE:
//                    title = String.format(getResources().getString(R.string.game_end_message));
//                    msg = getResources().getString(R.string.action_confirm_new_game);
                    break;
//                case NEW_GAME:
//                    titleId = R.string.action_confirm_new_game;
//                    msg = getResources().getString(R.string.new_game_settings_changed);
//                    break;
            }
            if (!title.isEmpty()) {
                builder.setTitle(title);
            } else {
                builder.setTitle(titleId);
            }
            if (!msg.equals("")) {
                builder.setMessage(msg);
            }
        }
        return builder.create();
    }

    public interface ConfirmDialogListener {
        void onConfirmDialogPositive(DialogTypes type);
        void onConfirmDialogNegative(DialogTypes type);
//        void onConfirmDialogNeutral(DialogTypes type);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case Dialog.BUTTON_POSITIVE:
                listener.onConfirmDialogPositive(type);
                break;
//            case Dialog.BUTTON_NEUTRAL:
//                listener.onConfirmDialogNeutral(type);
//                break;
            case Dialog.BUTTON_NEGATIVE:
                listener.onConfirmDialogNegative(type);
                break;
        }
    }
}