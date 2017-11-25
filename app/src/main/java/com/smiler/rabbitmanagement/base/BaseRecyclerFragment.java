package com.smiler.rabbitmanagement.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.smiler.rabbitmanagement.ManagementApplication;
import com.smiler.rabbitmanagement.R;
import com.smiler.rabbitmanagement.base.interfaces.FragmentListListener;
import com.smiler.rabbitmanagement.base.interfaces.UpdatableFragment;
import com.smiler.rabbitmanagement.preferences.Preferences;
import com.smiler.rabbitmanagement.views.DividerItemDecoration;

import lombok.Setter;


public abstract class BaseRecyclerFragment<T extends BaseViewModel> extends Fragment implements UpdatableFragment {
    public static final String TAG = "RMQ-BaseRecyclerFragment";

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    protected T dataModel;

    @Setter @Nullable
    protected FragmentListListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initModel();
        if (Preferences.getInstance(getContext()).isLoadOnOpen()) {
            updateData();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.base_list, container, false);
        ViewStub stub = rootView.findViewById(R.id.list_header);
        stub.setLayoutResource(getHeaderRes());
        stub.inflate();
        rootView.setTag(TAG);
        recyclerView = rootView.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        setRecyclerViewLayoutManager();
        recyclerView.setAdapter(initAdapter());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), R.drawable.list_divider));
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

    abstract public int getHeaderRes();

    abstract public void initModel();

    abstract public BaseRecyclerAdapter initAdapter();

    @Override
    public void updateData() {
        if (dataModel != null) {
            dataModel.loadData((ManagementApplication) getContext().getApplicationContext());
        }
    }
}
