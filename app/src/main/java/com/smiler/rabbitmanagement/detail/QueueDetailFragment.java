package com.smiler.rabbitmanagement.detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smiler.rabbitmanagement.R;
import com.smiler.rabbitmanagement.api.QueueDetailApi;


public class QueueDetailFragment extends Fragment {
    public static final String TAG = "RMQ-QueueDetailFragment";
    private QueueInfo data;

    public static QueueDetailFragment newInstance() {
        return new QueueDetailFragment();
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
        return rootView;
    }

    private void initData() {
        data = QueueDetailApi.getInfo();
    }

//    public void setListener(ListListener listener) {
//    }
}
