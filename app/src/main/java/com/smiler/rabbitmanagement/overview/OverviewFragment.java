package com.smiler.rabbitmanagement.overview;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.smiler.rabbitmanagement.ManagementApplication;
import com.smiler.rabbitmanagement.R;
import com.smiler.rabbitmanagement.base.TableRowValue;
import com.smiler.rabbitmanagement.base.interfaces.FragmentListListener;
import com.smiler.rabbitmanagement.base.interfaces.UpdatableFragment;
import com.smiler.rabbitmanagement.views.ValuePanel;
import com.smiler.rabbitmanagement.views.ValuesTable;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OverviewFragment extends Fragment implements UpdatableFragment {
    public static final String TAG = "RMQ-OverviewFragment";

    @BindView(R.id.overview_ready)
    ValuePanel valuePanelReady;
    @BindView(R.id.overview_unacked)
    ValuePanel valuePanelUnacked;
    @BindView(R.id.overview_total)
    ValuePanel valuePanelTotal;
    @BindView(R.id.container_info)
    LinearLayout infoContainer;

    private OverviewViewModel dataModel;

    public OverviewFragment() {
    }

    public static OverviewFragment newInstance() {
        return new OverviewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.overview, container, false);
        ButterKnife.bind(this, root);
        valuePanelReady.setTitle(getString(R.string.ready));
        valuePanelUnacked.setTitle(getString(R.string.unacked));
        valuePanelTotal.setTitle(getString(R.string.total));
        dataModel = ViewModelProviders.of(this).get(OverviewViewModel.class);

        final Observer<Overview> observer = data -> {
            if (data != null) {
                setView(data);
            }
        };
        dataModel.getModel().observe(this, observer);
        updateData();
        return root;
    }

    private void setView(final Overview data) {
        valuePanelReady.setValue(data.getQueueTotals().getMessagesReady());
        valuePanelUnacked.setValue(data.getQueueTotals().getMessagesUnacked());
        valuePanelTotal.setValue(data.getQueueTotals().getMessages());
        ArrayList<TableRowValue> globalCounts = new ArrayList<TableRowValue>() {{
            add(new TableRowValue(getString(R.string.queues), data.getObjectTotals().getQueues()));
            add(new TableRowValue(getString(R.string.connections), data.getObjectTotals().getConnections()));
            add(new TableRowValue(getString(R.string.channels), data.getObjectTotals().getChannels()));
            add(new TableRowValue(getString(R.string.exchanges), data.getObjectTotals().getExchanges()));
            add(new TableRowValue(getString(R.string.consumers), data.getObjectTotals().getConsumers()));
        }};
        ArrayList<TableRowValue> info = new ArrayList<TableRowValue>() {{
            add(new TableRowValue(getString(R.string.rabbit), data.getRabbitmqVersion()));
            add(new TableRowValue(getString(R.string.erlang), data.getErlangVersion()));
            add(new TableRowValue(getString(R.string.cluster), data.getClusterName()));
        }};
        infoContainer.removeAllViews();
        infoContainer.addView(new ValuesTable(getContext(), globalCounts));
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
