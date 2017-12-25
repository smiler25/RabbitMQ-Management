package com.smiler.rabbitadministration.queues;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smiler.rabbitadministration.R;
import com.smiler.rabbitadministration.base.BaseRecyclerAdapter;
import com.smiler.rabbitadministration.detail.QueueInfo;

import butterknife.BindView;
import lombok.Getter;


public class QueuesRecyclerAdapter extends BaseRecyclerAdapter<QueueInfo, QueuesRecyclerAdapter.ViewHolder> {
    QueuesRecyclerAdapter() {
        super();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.queues_list_row, viewGroup, false);
        return new ViewHolder(v);
    }

    static class ViewHolder extends BaseRecyclerAdapter.ViewHolder<QueueInfo> {
        @Getter
        protected QueueInfo item;
        @BindView(R.id.queue_name) TextView viewName;
        @BindView(R.id.queue_ready) TextView viewReady;
        @BindView(R.id.queue_unacked) TextView viewUnacked;
        @BindView(R.id.queue_total) TextView viewTotal;

        ViewHolder(View v) {
            super(v);
        }

        @SuppressLint("SetTextI18n")
        public void setView(QueueInfo value) {
            item = value;
            viewName.setText(value.getName());
            viewReady.setText(Long.toString(value.getReady()));
            viewUnacked.setText(Long.toString(value.getUnacked()));
            viewTotal.setText(Long.toString(value.getTotal()));
        };
    }
}
