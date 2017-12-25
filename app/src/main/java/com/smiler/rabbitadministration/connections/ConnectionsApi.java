package com.smiler.rabbitadministration.connections;

import com.smiler.rabbitadministration.ManagementApplication;
import com.smiler.rabbitadministration.base.api.BaseApi;
import com.smiler.rabbitadministration.base.api.RequestPath;

import java.util.ArrayList;

class ConnectionsApi {
    static void getList(ManagementApplication context, final BaseApi.ApiCallback<ArrayList<Connection>> callback) {
        BaseApi.requestList(context, callback, RequestPath.CONNECTIONS, Connection.class);
    }
}