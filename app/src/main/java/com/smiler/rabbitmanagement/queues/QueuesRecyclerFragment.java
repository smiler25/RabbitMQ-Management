package com.smiler.rabbitmanagement.queues;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.Toast;

import com.smiler.rabbitmanagement.R;
import com.smiler.rabbitmanagement.base.BaseRecyclerFragment;
import com.smiler.rabbitmanagement.detail.QueueInfo;

import java.util.ArrayList;


public class QueuesRecyclerFragment extends BaseRecyclerFragment<QueuesListViewModel> {
    public static final String TAG = "RMQ-QueuesRecyclerFragment";

    private QueuesRecyclerAdapter adapter;

    public static QueuesRecyclerFragment newInstance() {
        return new QueuesRecyclerFragment();
    }

    @Override
    public int getHeaderRes() {
        return R.layout.queues_list_header;
    }

    @Override
    public void initModel() {
        dataModel = ViewModelProviders.of(getActivity()).get(QueuesListViewModel.class);
        final Observer<ArrayList<QueueInfo>> observer = data -> {
            if (data != null) {
                adapter.updateData(data);
            }
        };
        dataModel.getModel().observe(this, observer);
    }

    public QueuesRecyclerAdapter initAdapter() {
        adapter = new QueuesRecyclerAdapter();
        adapter.setListener(obj -> Toast.makeText(getContext(), obj.toString(), Toast.LENGTH_LONG).show());
//        adapter.setListener(new BaseListListener<QueueInfo>() {
//            @Override
//            public void onListElementClick(QueueInfo queueInfo) {
//                System.out.println("queueInfo = " + queueInfo);
//                Toast.makeText(getContext(), queueInfo.toString(), Toast.LENGTH_LONG).show();
//            }
//        });
        return adapter;
    }
}
