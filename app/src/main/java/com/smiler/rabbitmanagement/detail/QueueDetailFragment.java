package com.smiler.rabbitmanagement.detail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smiler.rabbitmanagement.R;


public class QueueDetailFragment extends Fragment {
    public static final String TAG = "RMQ-QueueDetailFragment";
    private static final String ARG_NAME = "name";
    private String name;
    private QueueInfo data;

    public static QueueDetailFragment newInstance(String name) {
        QueueDetailFragment fragment = new QueueDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.base_list, container, false);
        rootView.setTag(TAG);
        return rootView;
    }

    private void initData() {
        data = QueueDetailApi.getInfo(name);
    }

    public void updateContent(String name) {
        this.name = name;
        initData();
    }

    //    public void setListener(QueueListListener listener) {
//    }
}
