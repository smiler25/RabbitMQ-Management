package com.smiler.rabbitadministration.base;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Toast;

import com.smiler.rabbitadministration.ManagementApplication;
import com.smiler.rabbitadministration.R;
import com.smiler.rabbitadministration.base.interfaces.FragmentListListener;
import com.smiler.rabbitadministration.base.interfaces.UpdatableFragment;
import com.smiler.rabbitadministration.base.interfaces.UpdatableFragmentListener;
import com.smiler.rabbitadministration.preferences.Preferences;
import com.smiler.rabbitadministration.views.DividerItemDecoration;

import lombok.Setter;


public abstract class BaseRecyclerFragment<T extends BaseViewModel> extends Fragment implements UpdatableFragment {
    public static final String TAG = "RMQ-BaseRecyclerFragment";

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    protected T dataModel;

    @Setter @Nullable
    protected FragmentListListener listener;
    @Setter @Nullable
    protected UpdatableFragmentListener callback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initModel();
        initErrorModel();
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

    public void initErrorModel() {
        final Observer<String > observerError = msg -> {
            if (msg != null) {
                Toast.makeText(getContext(), String.format(getString(R.string.api_error_overview), msg), Toast.LENGTH_LONG).show();
            }
            if (callback != null) {
                callback.stopLoading();
            }
        };
        dataModel.getError().observe(this, observerError);
    }

    abstract public BaseRecyclerAdapter initAdapter();

    @Override
    public void updateData() {
        if (callback != null) {
            callback.startLoading();
        }
        if (dataModel != null) {
            dataModel.loadData((ManagementApplication) getContext().getApplicationContext());
        }
    }
}
