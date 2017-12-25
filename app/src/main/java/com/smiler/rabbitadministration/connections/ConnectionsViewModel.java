package com.smiler.rabbitadministration.connections;

import com.smiler.rabbitadministration.ManagementApplication;
import com.smiler.rabbitadministration.base.BaseViewModel;
import com.smiler.rabbitadministration.base.api.BaseApi;

import java.util.ArrayList;

public class ConnectionsViewModel extends BaseViewModel<ArrayList<Connection>> {
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
                errorMessage.setValue(msg);
            }
        });
    }
}
