package com.smiler.rabbitadministration.queues;

import com.smiler.rabbitadministration.AppRepository;
import com.smiler.rabbitadministration.ManagementApplication;
import com.smiler.rabbitadministration.PageType;
import com.smiler.rabbitadministration.R;
import com.smiler.rabbitadministration.base.BaseRecyclerFragment;
import com.smiler.rabbitadministration.detail.QueueInfo;
import com.smiler.rabbitadministration.queues.filter.Filter;
import com.smiler.rabbitadministration.queues.sort.Sort;

import java.util.ArrayList;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


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
            if (callback != null) {
                callback.stopLoading();
            }
        };
        dataModel.getModel().observe(this, observer);
    }

    public QueuesRecyclerAdapter initAdapter() {
        adapter = new QueuesRecyclerAdapter();
        adapter.setListener(obj -> {
            if (listener != null) {
                listener.onListElementClick(PageType.QUEUE_DETAIL, obj);
            }
        });
        return adapter;
    }

    public Filter setQueuesFilter(Filter filter, boolean saveForProfile) {
        if (saveForProfile) {
            filter = AppRepository.getInstance(getContext().getApplicationContext()).insertFilter(filter);
        }
        dataModel.setFilter((ManagementApplication) getContext().getApplicationContext(), filter);
        return filter;
    }

    public void setQueuesOrder(Sort sort) {
        dataModel.setOrder(sort);
    }
}
