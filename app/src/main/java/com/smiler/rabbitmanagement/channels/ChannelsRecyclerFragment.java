package com.smiler.rabbitmanagement.channels;

import android.widget.Toast;

import com.smiler.rabbitmanagement.ManagementApplication;
import com.smiler.rabbitmanagement.R;
import com.smiler.rabbitmanagement.base.BaseRecyclerFragment;
import com.smiler.rabbitmanagement.base.api.BaseApi;
import com.smiler.rabbitmanagement.base.interfaces.BaseListListener;

import java.util.ArrayList;


public class ChannelsRecyclerFragment extends BaseRecyclerFragment {
    public static final String TAG = "RMQ-ChannelsRecyclerFragment";

    private ChannelsRecyclerAdapter adapter;

    public static ChannelsRecyclerFragment newInstance() {
        return new ChannelsRecyclerFragment();
    }

    @Override
    public int getHeaderRes() {
        return R.layout.channels_list_header;
    }

    public ChannelsRecyclerAdapter initAdapter() {
        adapter = new ChannelsRecyclerAdapter();
        adapter.setListener(new BaseListListener<Channel>() {
            @Override
            public void onListElementClick(Channel connection) {
                System.out.println("connection = " + connection);
                Toast.makeText(getContext(), connection.toString(), Toast.LENGTH_LONG).show();
            }
        });
        return adapter;
    }

    @Override
    public void updateData() {
        ChannelsApi.getList((ManagementApplication) getContext().getApplicationContext(), new BaseApi.ApiCallback<ArrayList<Channel>>() {
            @Override
            public void onResult(ArrayList<Channel> result) {
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
