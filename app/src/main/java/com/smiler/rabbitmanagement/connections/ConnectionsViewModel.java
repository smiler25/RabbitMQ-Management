package com.smiler.rabbitmanagement.connections;

import com.smiler.rabbitmanagement.ManagementApplication;
import com.smiler.rabbitmanagement.base.BaseViewModel;
import com.smiler.rabbitmanagement.base.api.BaseApi;

import java.util.ArrayList;

class ConnectionsViewModel extends BaseViewModel<ArrayList<Connection>> {
    protected void loadData(ManagementApplication context) {
        ConnectionsApi.getList(context, new BaseApi.ApiCallback<ArrayList<Connection>>() {
            @Override
            public void onResult(ArrayList<Connection> result) {
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
