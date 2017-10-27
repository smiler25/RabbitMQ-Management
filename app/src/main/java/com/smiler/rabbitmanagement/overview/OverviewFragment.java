package com.smiler.rabbitmanagement.overview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.smiler.rabbitmanagement.R;
import com.smiler.rabbitmanagement.api.OverviewApi;
import com.smiler.rabbitmanagement.views.OverviewPanel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OverviewFragment extends Fragment {
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
        OverviewApi.getInfo(getContext(), new OverviewApi.OverviewApiCallback() {
            @Override
            public void onResult(Overview result) {
                overviewPanelReady.setTitle("Ready").setValue(result.getQueueTotals().getMessagesReady());
                overviewPanelUnacked.setTitle("Unacked").setValue(result.getQueueTotals().getMessagesUnacked());
                overviewPanelTotal.setTitle("Total").setValue(result.getQueueTotals().getMessages());
            }

            @Override
            public void onError() {
                Toast.makeText(getContext(), "Get overview error", Toast.LENGTH_LONG).show();
            }
        });
        View root = inflater.inflate(R.layout.overview, container, false);
        ButterKnife.bind(this, root);
        return root;
    }
}
