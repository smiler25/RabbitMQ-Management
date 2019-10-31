package com.smiler.rabbitadministration.connections;

import com.smiler.rabbitadministration.PageType;
import com.smiler.rabbitadministration.R;
import com.smiler.rabbitadministration.base.BaseRecyclerFragment;

import java.util.ArrayList;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


public class ConnectionsRecyclerFragment extends BaseRecyclerFragment<ConnectionsViewModel> {
    public static final String TAG = "RMQ-ConnectionsRecyclerFragment";

    private ConnectionsRecyclerAdapter adapter;

    public static ConnectionsRecyclerFragment newInstance() {
        return new ConnectionsRecyclerFragment();
    }

    @Override
    public int getHeaderRes() {
        return R.layout.connections_list_header;
    }

    @Override
    public void initModel() {
        dataModel = ViewModelProviders.of(getActivity()).get(ConnectionsViewModel.class);
        final Observer<ArrayList<Connection>> observer = data -> {
            if (data != null) {
                adapter.updateData(data);
            }
            if (callback != null) {
                callback.stopLoading();
            }
        };
        dataModel.getModel().observe(this, observer);
    }

    public ConnectionsRecyclerAdapter initAdapter() {
        adapter = new ConnectionsRecyclerAdapter();
        adapter.setListener(obj -> {
            if (listener != null) {
                listener.onListElementClick(PageType.CONNECTIONS, obj);
            }
        });
        return adapter;
    }
}
