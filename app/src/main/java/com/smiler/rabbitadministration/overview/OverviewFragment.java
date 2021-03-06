package com.smiler.rabbitadministration.overview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.smiler.rabbitadministration.ManagementApplication;
import com.smiler.rabbitadministration.R;
import com.smiler.rabbitadministration.base.TableRowValue;
import com.smiler.rabbitadministration.base.interfaces.FragmentListListener;
import com.smiler.rabbitadministration.base.interfaces.UpdatableFragment;
import com.smiler.rabbitadministration.base.interfaces.UpdatableFragmentListener;
import com.smiler.rabbitadministration.preferences.Preferences;
import com.smiler.rabbitadministration.views.ValuePanel;
import com.smiler.rabbitadministration.views.ValuesTable;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Setter;

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

    @Setter @Nullable
    protected UpdatableFragmentListener callback;

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
            if (callback != null) {
                callback.stopLoading();
            }
        };
        final Observer<String > observerError = msg -> {
            if (msg != null) {
                Toast.makeText(getContext(), String.format(getString(R.string.api_error_overview), msg), Toast.LENGTH_LONG).show();
            }
            if (callback != null) {
                callback.stopLoading();
            }
        };
        dataModel.getModel().observe(this, observer);
        dataModel.getError().observe(this, observerError);
        if (Preferences.getInstance(getContext()).isLoadOnOpen()) {
            updateData();
        }
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
        if (callback != null) {
            callback.startLoading();
        }
        if (dataModel != null) {
            dataModel.loadData((ManagementApplication) getContext().getApplicationContext());
        }
    }

    @Override
    public void setListener(FragmentListListener listener) {

    }
}
