package com.smiler.rabbitmanagement.connections;

import android.os.Bundle;

import com.smiler.rabbitmanagement.base.DetailFragment;
import com.smiler.rabbitmanagement.base.HumanString;
import com.smiler.rabbitmanagement.base.TableRowValue;
import com.smiler.rabbitmanagement.views.ValuesTable;

import java.util.ArrayList;


public class ConnectionDetailFragment extends DetailFragment<Connection> {
    public static final String TAG = "RMQ-ConnectionDetailFragment";

    public static ConnectionDetailFragment newInstance() {
        return new ConnectionDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initData();
    }

    protected void setView(final Connection data) {
        ArrayList<TableRowValue> info1 = new ArrayList<TableRowValue>() {{
            for (HumanString one : data.getHumanStrings()) {
                add(new TableRowValue(getString(one.getResId()), one.getValue()));
            }
        }};
        ArrayList<TableRowValue> info2 = new ArrayList<TableRowValue>() {{
            for (HumanString one : data.getHumanStringsClient()) {
                add(new TableRowValue(getString(one.getResId()), one.getValue()));
            }
        }};
        infoContainer.removeAllViews();
        infoContainer.addView(new ValuesTable(getContext(), info1));
        infoContainer.addView(new ValuesTable(getContext(), info2));
    }
}
