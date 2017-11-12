package com.smiler.rabbitmanagement.connections;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.widget.Toast;

import com.smiler.rabbitmanagement.R;
import com.smiler.rabbitmanagement.base.BaseRecyclerFragment;

import java.util.ArrayList;


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
        };
        dataModel.getModel().observe(this, observer);
    }

    public ConnectionsRecyclerAdapter initAdapter() {
        adapter = new ConnectionsRecyclerAdapter();
        adapter.setListener(obj -> Toast.makeText(getContext(), obj.toString(), Toast.LENGTH_LONG).show());
        return adapter;
    }
}
