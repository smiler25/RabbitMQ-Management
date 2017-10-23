package com.smiler.rabbitmanagement.queues;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smiler.rabbitmanagement.R;
import com.smiler.rabbitmanagement.api.QueuesListApi;
import com.smiler.rabbitmanagement.base.interfaces.ListListener;
import com.smiler.rabbitmanagement.detail.QueueInfo;

import java.util.ArrayList;


public class QueuesRecyclerFragment extends Fragment {
    public static final String TAG = "RMQ-QueuesRecyclerFragment";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private QueuesRecyclerAdapter adapter;
    private ListListener listener;
    private ArrayList<QueueInfo> data;

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
        adapter = new QueuesRecyclerAdapter(data);
    }

    private void initData() {
        data = getList();
    }

    private ArrayList<QueueInfo> getList() {
        return QueuesListApi.getList();
    }

    public boolean updateList() {
        adapter.notifyDataSetChanged();
        return adapter.getItemCount() == 0;
    }

    public void setListener(ListListener listener) {
        this.listener = listener;
        adapter.setListener(listener);
    }

//    public void setMode(CABListener listener) {}

//    public void clearSelection() {
//        adapter.clearSelection();
//    }

//    public void deleteSelection() {
//        adapter.deleteSelection();
//    }
}
