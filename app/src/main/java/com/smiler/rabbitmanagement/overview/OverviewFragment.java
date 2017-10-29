package com.smiler.rabbitmanagement.overview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.smiler.rabbitmanagement.ManagementApplication;
import com.smiler.rabbitmanagement.R;
import com.smiler.rabbitmanagement.api.OverviewApi;
import com.smiler.rabbitmanagement.views.OverviewPanel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OverviewFragment extends Fragment {
    public static final String TAG = "RMQ-OverviewFragment";

    @BindView(R.id.overview_ready)
    OverviewPanel overviewPanelReady;
    @BindView(R.id.overview_unacked)
    OverviewPanel overviewPanelUnacked;
    @BindView(R.id.overview_total)
    OverviewPanel overviewPanelTotal;

    public OverviewFragment() {
    }

    public static OverviewFragment newInstance() {
        return new OverviewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        requestData();
        View root = inflater.inflate(R.layout.overview, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    public void requestData() {
        OverviewApi.getInfo((ManagementApplication) getContext().getApplicationContext(), new OverviewApi.OverviewApiCallback() {
            @Override
            public void onResult(Overview result) {
                overviewPanelReady.setTitle(getString(R.string.ready)).setValue(result.getQueueTotals().getMessagesReady());
                overviewPanelUnacked.setTitle(getString(R.string.unacked)).setValue(result.getQueueTotals().getMessagesUnacked());
                overviewPanelTotal.setTitle(getString(R.string.total)).setValue(result.getQueueTotals().getMessages());
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(getContext(), String.format(getString(R.string.api_error_overview), msg), Toast.LENGTH_LONG).show();
            }
        });
    }
}
