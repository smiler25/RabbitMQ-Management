package com.smiler.rabbitmanagement.queues;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smiler.rabbitmanagement.R;
import com.smiler.rabbitmanagement.base.interfaces.QueueListListener;
import com.smiler.rabbitmanagement.detail.QueueInfo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;
import lombok.Setter;


public class QueuesRecyclerAdapter extends RecyclerView.Adapter<QueuesRecyclerAdapter.ViewHolder> {
    private static final String TAG = "RMQ-QueuesRecyclerAdapter";
    @Setter
    private QueueListListener listener;
    private ArrayList<QueueInfo> data;

    QueuesRecyclerAdapter() {
        super();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.queues_list_row, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        QueueInfo value = data.get(position);
        viewHolder.setView(value);
        viewHolder.setCallback(listener);
        if((position % 2 == 0)){
            viewHolder.root.setBackgroundResource(R.color.listEven);
        } else {
            viewHolder.root.setBackgroundResource(R.color.listOdd);
        }
    }

    void updateData(ArrayList<QueueInfo> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Getter
        private QueueInfo queueInfo;
        @Setter
        private QueueListListener callback;
        private final View root;
        @BindView(R.id.queue_name) TextView viewName;
        @BindView(R.id.queue_ready) TextView viewReady;
        @BindView(R.id.queue_unacked) TextView viewUnacked;
        @BindView(R.id.queue_total) TextView viewTotal;

        ViewHolder(View v) {
            super(v);
            root = v;
            ButterKnife.bind(this, root);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null) {
                        callback.onListElementClick(queueInfo);
                    }
                }
            });
        }

        @SuppressLint("SetTextI18n")
        public void setView(QueueInfo value) {
            queueInfo = value;
            viewName.setText(value.getName());
            viewReady.setText(Long.toString(value.getReady()));
            viewUnacked.setText(Long.toString(value.getUnacked()));
            viewTotal.setText(Long.toString(value.getTotal()));
        };
    }
}
