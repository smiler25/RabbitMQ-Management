package com.smiler.rabbitmanagement.channels;

import android.os.Bundle;

import com.smiler.rabbitmanagement.base.DetailFragment;
import com.smiler.rabbitmanagement.base.HumanString;
import com.smiler.rabbitmanagement.base.TableRowValue;
import com.smiler.rabbitmanagement.views.ValuesTable;

import java.util.ArrayList;


public class ChannelDetailFragment extends DetailFragment<Channel> {
    public static final String TAG = "RMQ-ChannelDetailFragment";

    public static ChannelDetailFragment newInstance() {
        return new ChannelDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        initData();
    }

    protected void setView(final Channel data) {
        ArrayList<TableRowValue> info = new ArrayList<TableRowValue>() {{
            for (HumanString one : data.getHumanStrings()) {
                add(new TableRowValue(getString(one.getResId()), one.getValue()));
            }
        }};
        infoContainer.removeAllViews();
        infoContainer.addView(new ValuesTable(getContext(), info));
    }
}
