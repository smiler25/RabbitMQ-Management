package com.smiler.rabbitmanagement.channels;

import com.smiler.rabbitmanagement.ManagementApplication;
import com.smiler.rabbitmanagement.base.BaseViewModel;
import com.smiler.rabbitmanagement.base.api.BaseApi;

import java.util.ArrayList;

class ChannelsViewModel extends BaseViewModel<ArrayList<Channel>> {
    protected void loadData(ManagementApplication context) {
        ChannelsApi.getList(context, new BaseApi.ApiCallback<ArrayList<Channel>>() {
            @Override
            public void onResult(ArrayList<Channel> result) {
                if (result != null) {
                    data.setValue(result);
                }
            }

            @Override
            public void onError(String msg) {
//                Toast.makeText(getContext(), String.format(getString(R.string.api_error_queues), msg), Toast.LENGTH_LONG).show();
            }
        });
    }
}
