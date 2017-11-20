package com.smiler.rabbitmanagement.detail;

import com.smiler.rabbitmanagement.ManagementApplication;
import com.smiler.rabbitmanagement.base.api.BaseApi;
import com.smiler.rabbitmanagement.base.api.RequestPath;

class QueueDetailApi {
    static void getInfo(ManagementApplication context, String vhost, String name, final BaseApi.ApiCallback<QueueInfo> callback) {
        BaseApi.requestObject(context, callback, String.format(RequestPath.QUEUE_DETAIL, (vhost.equals(RequestPath.DEFAULT_VHOST) ? RequestPath.DEFAULT_VHOST_URL : vhost), name), QueueInfo.class);
    }
}
