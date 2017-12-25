package com.smiler.rabbitadministration.channels;

import com.smiler.rabbitadministration.ManagementApplication;
import com.smiler.rabbitadministration.base.BaseViewModel;
import com.smiler.rabbitadministration.base.api.BaseApi;

import java.util.ArrayList;

public class ChannelsViewModel extends BaseViewModel<ArrayList<Channel>> {
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
                errorMessage.setValue(msg);
            }
        });
    }
}
