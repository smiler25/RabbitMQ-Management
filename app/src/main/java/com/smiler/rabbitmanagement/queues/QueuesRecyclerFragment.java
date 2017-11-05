package com.smiler.rabbitmanagement.queues;

import android.widget.Toast;

import com.smiler.rabbitmanagement.ManagementApplication;
import com.smiler.rabbitmanagement.R;
import com.smiler.rabbitmanagement.base.BaseRecyclerFragment;
import com.smiler.rabbitmanagement.base.api.BaseApi;
import com.smiler.rabbitmanagement.base.interfaces.BaseListListener;
import com.smiler.rabbitmanagement.detail.QueueInfo;

import java.util.ArrayList;


public class QueuesRecyclerFragment extends BaseRecyclerFragment {
    public static final String TAG = "RMQ-QueuesRecyclerFragment";

    private QueuesRecyclerAdapter adapter;

    public static QueuesRecyclerFragment newInstance() {
        return new QueuesRecyclerFragment();
    }

    @Override
    public int getHeaderRes() {
        return R.layout.queues_list_header;
    }

    public QueuesRecyclerAdapter initAdapter() {
        adapter = new QueuesRecyclerAdapter();
        adapter.setListener(new BaseListListener<QueueInfo>() {
            @Override
            public void onListElementClick(QueueInfo queueInfo) {
                System.out.println("queueInfo = " + queueInfo);
                Toast.makeText(getContext(), queueInfo.toString(), Toast.LENGTH_LONG).show();
            }
        });
        return adapter;
    }

    @Override
    public void updateData() {
        QueuesListApi.getList((ManagementApplication) getContext().getApplicationContext(), new BaseApi.ApiCallback<ArrayList<QueueInfo>>() {
            @Override
            public void onResult(ArrayList<QueueInfo> result) {
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
