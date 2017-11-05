package com.smiler.rabbitmanagement.connections;

import android.widget.Toast;

import com.smiler.rabbitmanagement.ManagementApplication;
import com.smiler.rabbitmanagement.R;
import com.smiler.rabbitmanagement.base.BaseRecyclerFragment;
import com.smiler.rabbitmanagement.base.api.BaseApi;
import com.smiler.rabbitmanagement.base.interfaces.BaseListListener;

import java.util.ArrayList;


public class ConnectionsRecyclerFragment extends BaseRecyclerFragment {
    public static final String TAG = "RMQ-ConnectionsRecyclerFragment";

    private ConnectionsRecyclerAdapter adapter;

    public static ConnectionsRecyclerFragment newInstance() {
        return new ConnectionsRecyclerFragment();
    }

    @Override
    public int getHeaderRes() {
        return R.layout.connections_list_header;
    }

    public ConnectionsRecyclerAdapter initAdapter() {
        adapter = new ConnectionsRecyclerAdapter();
        adapter.setListener(new BaseListListener<Connection>() {
            @Override
            public void onListElementClick(Connection connection) {
                System.out.println("connection = " + connection);
                Toast.makeText(getContext(), connection.toString(), Toast.LENGTH_LONG).show();
            }
        });
        return adapter;
    }

    @Override
    public void updateData() {
        ConnectionsApi.getList((ManagementApplication) getContext().getApplicationContext(), new BaseApi.ApiCallback<ArrayList<Connection>>() {
            @Override
            public void onResult(ArrayList<Connection> result) {
                if (result != null) {
                    adapter.updateData(result);
                }
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(getContext(), String.format(getString(R.string.api_error_queues), msg), Toast.LENGTH_LONG).show();
            }
        });
    }
}
