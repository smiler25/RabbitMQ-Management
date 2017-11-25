package com.smiler.rabbitmanagement.detail;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.smiler.rabbitmanagement.ManagementApplication;
import com.smiler.rabbitmanagement.R;
import com.smiler.rabbitmanagement.base.DetailFragment;
import com.smiler.rabbitmanagement.base.HumanString;
import com.smiler.rabbitmanagement.base.TableRowValue;
import com.smiler.rabbitmanagement.base.interfaces.FragmentListListener;
import com.smiler.rabbitmanagement.base.interfaces.UpdatableFragment;
import com.smiler.rabbitmanagement.base.interfaces.UpdatableFragmentListener;
import com.smiler.rabbitmanagement.views.ValuePanel;
import com.smiler.rabbitmanagement.views.ValuesTable;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Setter;


public class QueueDetailFragment extends DetailFragment<QueueInfo> implements UpdatableFragment {
    public static final String TAG = "RMQ-QueueDetailFragment";
    private static final String ARG_NAME = "name";

    @Setter @Nullable
    protected UpdatableFragmentListener callback;
    @Setter
    private QueueInfo data;

    private QueueDetailViewModel dataModel;

    @BindView(R.id.qdetail_ready)
    ValuePanel panelReady;
    @BindView(R.id.qdetail_unacked)
    ValuePanel panelUnacked;
    @BindView(R.id.qdetail_total)
    ValuePanel panelTotal;
    @BindView(R.id.container_info)
    LinearLayout infoContainer;


    public static QueueDetailFragment newInstance(String name) {
        QueueDetailFragment fragment = new QueueDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    public static QueueDetailFragment newInstance() {
        return new QueueDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.queue_detail, container, false);
        ButterKnife.bind(this, root);
        panelReady.setTitle(getString(R.string.ready));
        panelUnacked.setTitle(getString(R.string.unacked));
        panelTotal.setTitle(getString(R.string.total));
        dataModel = ViewModelProviders.of(this).get(QueueDetailViewModel.class);

        final Observer<QueueInfo> observer = data -> {
            if (data != null) {
                setView(data);
            }
        };
        dataModel.getModel().observe(this, observer);
//        updateData();
        setView(data);
        return root;
    }

    protected void setView(final QueueInfo data) {
        panelReady.setValue(Long.toString(data.getReady()));
        panelUnacked.setValue(Long.toString(data.getUnacked()));
        panelTotal.setValue(Long.toString(data.getTotal()));
        ArrayList<TableRowValue> info = new ArrayList<TableRowValue>() {{
            for (HumanString one : data.getHumanStrings()) {
                add(new TableRowValue(getString(one.getResId()), one.getValue()));
            }
        }};
        infoContainer.removeAllViews();
        infoContainer.addView(new ValuesTable(getContext(), info));
    }

    @Override
    public void updateData() {
        if (dataModel != null) {
            dataModel.loadData((ManagementApplication) getContext().getApplicationContext());
        }
    }

    @Override
    public void setListener(FragmentListListener listener) {

    }

}
