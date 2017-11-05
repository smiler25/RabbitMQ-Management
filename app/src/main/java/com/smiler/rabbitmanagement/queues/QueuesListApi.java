package com.smiler.rabbitmanagement.queues;

import com.smiler.rabbitmanagement.ManagementApplication;
import com.smiler.rabbitmanagement.base.api.BaseApi;
import com.smiler.rabbitmanagement.base.api.RequestPath;
import com.smiler.rabbitmanagement.detail.QueueInfo;

import java.util.ArrayList;

class QueuesListApi {
    static void getList(ManagementApplication context, final BaseApi.ApiCallback<ArrayList<QueueInfo>> callback) {
        BaseApi.requestList(context, callback, RequestPath.QUEUES_LIST, QueueInfo.class);
    }
}
