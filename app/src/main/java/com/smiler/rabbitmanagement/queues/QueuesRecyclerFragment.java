package com.smiler.rabbitmanagement.queues;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.smiler.rabbitmanagement.R;
import com.smiler.rabbitmanagement.api.QueuesListApi;
import com.smiler.rabbitmanagement.base.interfaces.QueueListListener;
import com.smiler.rabbitmanagement.detail.QueueInfo;

import java.util.ArrayList;


public class QueuesRecyclerFragment extends Fragment {
    public static final String TAG = "RMQ-QueuesRecyclerFragment";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private QueuesRecyclerAdapter adapter;

    public static QueuesRecyclerFragment newInstance() {
        return new QueuesRecyclerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.queues_list, container, false);
        rootView.setTag(TAG);
        recyclerView = rootView.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        setRecyclerViewLayoutManager();
        initAdapter();
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    public void setRecyclerViewLayoutManager() {
        int scrollPosition = 0;
        if (recyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) recyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.scrollToPosition(scrollPosition);
    }

    private void initAdapter() {
        adapter = new QueuesRecyclerAdapter();
        adapter.setListener(new QueueListListener() {
            @Override
            public void onListElementClick(QueueInfo queueInfo) {
                System.out.println("queueInfo = " + queueInfo);
                Toast.makeText(getContext(), queueInfo.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initData() {
        QueuesListApi.getList(getContext(), new QueuesListApi.QueuesListApiCallback() {
            @Override
            public void onResult(ArrayList<QueueInfo> result) {
                if (result != null) {
                    adapter.updateData(result);
                }
            }

            @Override
            public void onError() {

            }
        });
    }
}
