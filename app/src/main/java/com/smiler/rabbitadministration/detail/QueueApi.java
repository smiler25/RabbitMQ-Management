package com.smiler.rabbitadministration.detail;

import com.smiler.rabbitadministration.ManagementApplication;
import com.smiler.rabbitadministration.base.api.BaseApi;
import com.smiler.rabbitadministration.base.api.RequestPath;

class QueueApi {
    static void getInfo(ManagementApplication context, String vhost, String name, final BaseApi.ApiCallback<QueueInfo> callback) {
        BaseApi.getObject(context, callback, String.format(RequestPath.QUEUE_DETAIL, (vhost.equals(RequestPath.DEFAULT_VHOST) ? RequestPath.DEFAULT_VHOST_URL : vhost), name), QueueInfo.class);
    }

    static void delete(ManagementApplication context, String vhost, String name, final BaseApi.ApiCallback<Boolean> callback) {
        BaseApi.delete(context, callback, String.format(RequestPath.QUEUE_DELETE, (vhost.equals(RequestPath.DEFAULT_VHOST) ? RequestPath.DEFAULT_VHOST_URL : vhost), name), QueueInfo.class);
    }

    static void purge(ManagementApplication context, String vhost, String name, final BaseApi.ApiCallback<Boolean> callback) {
        BaseApi.delete(context, callback, String.format(RequestPath.QUEUE_PURGE, (vhost.equals(RequestPath.DEFAULT_VHOST) ? RequestPath.DEFAULT_VHOST_URL : vhost), name), QueueInfo.class);
    }

//    static void moveMessages(ManagementApplication context, String vhost, String name, String newVhost, String newName, final BaseApi.ApiCallback<Boolean> callback) {
//        BaseApi.getObject(context, callback, String.format(RequestPath.QUEUE_MOVE, (vhost.equals(RequestPath.DEFAULT_VHOST) ? RequestPath.DEFAULT_VHOST_URL : vhost), name), QueueInfo.class);
//    }
}
