package com.smiler.rabbitmanagement.queues;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;

import com.smiler.rabbitmanagement.AppRepository;
import com.smiler.rabbitmanagement.ManagementApplication;
import com.smiler.rabbitmanagement.PageType;
import com.smiler.rabbitmanagement.R;
import com.smiler.rabbitmanagement.base.BaseRecyclerFragment;
import com.smiler.rabbitmanagement.detail.QueueInfo;
import com.smiler.rabbitmanagement.queues.filter.Filter;
import com.smiler.rabbitmanagement.queues.sort.Sort;

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
        adapter.setListener(obj -> {
            if (listener != null) {
                listener.onListElementClick(PageType.QUEUES, obj);
            }
        });
        return adapter;
    }

    public void setQueuesFilter(Filter filter, boolean saveForProfile) {
        if (saveForProfile) {
            AppRepository.getInstance(getContext().getApplicationContext()).insertFilter(filter);
        }
        dataModel.setFilter((ManagementApplication) getContext().getApplicationContext(), filter);
    }

    public void setQueuesOrder(Sort sort) {
        dataModel.setOrder(sort);
    }
}
