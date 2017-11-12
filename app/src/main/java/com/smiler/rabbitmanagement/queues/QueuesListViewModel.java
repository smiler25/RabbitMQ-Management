package com.smiler.rabbitmanagement.queues;

import android.support.annotation.Nullable;

import com.smiler.rabbitmanagement.ManagementApplication;
import com.smiler.rabbitmanagement.base.BaseViewModel;
import com.smiler.rabbitmanagement.base.api.BaseApi;
import com.smiler.rabbitmanagement.detail.QueueInfo;
import com.smiler.rabbitmanagement.queues.filter.Filter;

import java.util.ArrayList;

import lombok.Setter;

class QueuesListViewModel extends BaseViewModel<ArrayList<QueueInfo>> {
    @Setter @Nullable
    private Filter filter;

    protected void loadData(ManagementApplication context) {
        QueuesListApi.getList(context, new BaseApi.ApiCallback<ArrayList<QueueInfo>>() {
            @Override
            public void onResult(ArrayList<QueueInfo> result) {
                if (result != null) {
                    data.setValue(getFilteredData(result));
                }
            }

            @Override
            public void onError(String msg) {
//                Toast.makeText(getContext(), String.format(getString(R.string.api_error_queues), msg), Toast.LENGTH_LONG).show();
            }
        });
    }

    private ArrayList<QueueInfo> getFilteredData(ArrayList<QueueInfo> data) {
        if (filter == null || filter.getValue().isEmpty()) {
            return data;
        }
        return new ArrayList<>();
    }
}
