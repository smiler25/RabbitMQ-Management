package com.smiler.rabbitadministration.channels;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;

import com.smiler.rabbitadministration.PageType;
import com.smiler.rabbitadministration.R;
import com.smiler.rabbitadministration.base.BaseRecyclerFragment;

import java.util.ArrayList;


public class ChannelsRecyclerFragment extends BaseRecyclerFragment<ChannelsViewModel> {
    public static final String TAG = "RMQ-ChannelsRecyclerFragment";

    private ChannelsRecyclerAdapter adapter;

    public static ChannelsRecyclerFragment newInstance() {
        return new ChannelsRecyclerFragment();
    }

    @Override
    public int getHeaderRes() {
        return R.layout.channels_list_header;
    }

    @Override
    public void initModel() {
        dataModel = ViewModelProviders.of(getActivity()).get(ChannelsViewModel.class);
        final Observer<ArrayList<Channel>> observer = data -> {
            if (data != null) {
                adapter.updateData(data);
            }
            if (callback != null) {
                callback.stopLoading();
            }
        };
        dataModel.getModel().observe(this, observer);

    }

    public ChannelsRecyclerAdapter initAdapter() {
        adapter = new ChannelsRecyclerAdapter();
        adapter.setListener(obj -> {
            if (listener != null) {
                listener.onListElementClick(PageType.CHANNELS, obj);
            }
        });
        return adapter;
    }
}