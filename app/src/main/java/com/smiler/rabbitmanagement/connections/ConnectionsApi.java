package com.smiler.rabbitmanagement.connections;

import com.smiler.rabbitmanagement.ManagementApplication;
import com.smiler.rabbitmanagement.base.api.BaseApi;
import com.smiler.rabbitmanagement.base.api.RequestPath;

import java.util.ArrayList;

class ConnectionsApi {
    static void getList(ManagementApplication context, final BaseApi.ApiCallback<ArrayList<Connection>> callback) {
        BaseApi.requestList(context, callback, RequestPath.CONNECTIONS, Connection.class);
    }
}