package com.smiler.rabbitadministration.queues;

import com.smiler.rabbitadministration.ManagementApplication;
import com.smiler.rabbitadministration.base.api.BaseApi;
import com.smiler.rabbitadministration.base.api.RequestPath;
import com.smiler.rabbitadministration.detail.QueueInfo;

import java.util.ArrayList;

class QueuesListApi {
    static void getList(ManagementApplication context, final BaseApi.ApiCallback<ArrayList<QueueInfo>> callback) {
        BaseApi.requestList(context, callback, RequestPath.QUEUES_LIST, QueueInfo.class);
    }
}
