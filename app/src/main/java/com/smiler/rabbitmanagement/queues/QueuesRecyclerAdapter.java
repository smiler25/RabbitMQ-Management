package com.smiler.rabbitmanagement.queues;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smiler.rabbitmanagement.R;
import com.smiler.rabbitmanagement.base.interfaces.ItemsCallback;
import com.smiler.rabbitmanagement.base.interfaces.ListListener;
import com.smiler.rabbitmanagement.detail.QueueInfo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;


public class QueuesRecyclerAdapter extends RecyclerView.Adapter<QueuesRecyclerAdapter.ViewHolder> {
    private static final String TAG = "RMQ-QueuesRecyclerAdapter";
    private ListListener listener;
    private boolean multiSelection = false;
    private View selectedItem;
    private ItemsCallback callback;
    private ArrayList<Integer> selectedIds = new ArrayList<>();
    private final ArrayList<QueueInfo> data;

    public QueuesRecyclerAdapter(ArrayList<QueueInfo> data) {
        super();
        this.data = data;
        callback = new ItemsCallback() {
            @Override
            public void onElementLongClick(View view) {
                if (!multiSelection) {
                    multiSelection = true;
                    selectedIds.clear();
                    if (selectedItem != null) {
                        selectedItem.setSelected(false);
                    }
                    listener.onListElementLongClick(0);
                }
            }

            @Override
            public void onElementClick(View view) {
                view.setSelected(!view.isSelected());
                if (multiSelection) {
                    if (view.isSelected()) {
                        selectedIds.add((Integer) view.getTag());
                    } else {
                        selectedIds.remove((Integer) view.getTag());
                    }
                    listener.onListElementClick(selectedIds.size());
                } else {
                    selectedIds.clear();
                    if (selectedItem != null) {
                        selectedItem.setSelected(false);
                    }
                    selectedItem = view;
                    listener.onListElementClick((int) view.getTag());
                }
            }
        };
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
//        viewHolder.setId(value.getId());
//        viewHolder.setSelected(selectedIds.indexOf(value.getId()) != -1);
        viewHolder.setCallback(callback);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

//    public void deleteSelection() {
////        if (selectedIds.size() > 0) {
////            RealmController.with().deleteResults(selectedIds.toArray(new Integer[selectedIds.size()]));
////        }
////        selectedItem = null;
////        selectedIds.clear();
////        multiSelection = false;
////        notifyDataSetChanged();
//    }

    public void setListener(ListListener listener) {
        this.listener = listener;
    }

//    public void clearSelection() {
//        if (selectedItem != null) {
//            selectedItem.setSelected(false);
//            selectedItem = null;
//        }
//        selectedIds.clear();
//        multiSelection = false;
//        notifyDataSetChanged();
//    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Getter
        private String name;
        private final View root;
        private ItemsCallback callback;
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
                        callback.onElementClick(root);
                    }
                }
            });
            v.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (callback != null) {
                        callback.onElementLongClick(root);
                    }
                    return false;
                }
            });
        }

        public void setView(QueueInfo value) {
            name = value.getName();
            viewName.setText(value.getName());
            viewReady.setText(Long.toString(value.getReady()));
            viewUnacked.setText(Long.toString(value.getUnacked()));
            viewTotal.setText(Long.toString(value.getTotal()));
        };

        public void setCallback(ItemsCallback callback) { this.callback = callback; }

//        public void setSelected(boolean selected) {
//            root.setSelected(selected);
//        }
    }
}
