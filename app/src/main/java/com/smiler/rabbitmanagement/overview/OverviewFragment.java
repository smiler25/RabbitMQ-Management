package com.smiler.rabbitmanagement.overview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smiler.rabbitmanagement.R;
import com.smiler.rabbitmanagement.api.OverviewApi;

public class OverviewFragment extends Fragment {

    public OverviewFragment() {
    }

    public static OverviewFragment newInstance() {
        return new OverviewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        OverviewApi.getInfo(getContext());
//        TextView textView = rootView.findViewById(R.id.section_label);
//        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        return inflater.inflate(R.layout.overview, container, false);
    }
}
