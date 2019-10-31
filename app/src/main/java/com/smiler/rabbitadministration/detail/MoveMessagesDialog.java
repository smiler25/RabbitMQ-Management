package com.smiler.rabbitadministration.detail;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.smiler.rabbitadministration.R;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Setter;
import lombok.experimental.Accessors;


public class MoveMessagesDialog extends DialogFragment implements DialogInterface.OnClickListener {
    @Setter @Accessors(chain = true)
    private String queueName;

    @Setter @Accessors(chain = true) @Nullable
    private MoveMessagesDialogListener listener;

    @BindView(R.id.target_queue)
    EditText targetQueue;

    public static MoveMessagesDialog newInstance() {
        return new MoveMessagesDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_move, null);
        ButterKnife.bind(this, v);
        return new AlertDialog.Builder(getActivity())
                .setPositiveButton(R.string.confirm, this)
                .setNegativeButton(R.string.cancel, this)
                .setTitle(R.string.move_messages)
                .setMessage(String.format(getResources().getString(R.string.move_messages_dialog_text), queueName))
                .setView(v)
                .setCancelable(true)
                .create();
    }

    public interface MoveMessagesDialogListener {
        void onMoveMessagesPositive(String targetQueue);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (listener == null) {
            return;
        }
        if (which == Dialog.BUTTON_POSITIVE) {
            String value = targetQueue.getText().toString().trim();
            if (value.isEmpty()) {
                Toast.makeText(getActivity(), R.string.move_messages_empty_value, Toast.LENGTH_LONG).show();
                return;
            }
            listener.onMoveMessagesPositive(value);
        }
    }
}