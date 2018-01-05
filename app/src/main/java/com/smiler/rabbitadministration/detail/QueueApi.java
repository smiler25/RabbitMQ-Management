package com.smiler.rabbitadministration.detail;

import com.smiler.rabbitadministration.ManagementApplication;
import com.smiler.rabbitadministration.base.api.BaseApi;
import com.smiler.rabbitadministration.base.api.RequestPath;

import org.json.JSONException;
import org.json.JSONObject;

import static com.smiler.rabbitadministration.Constants.AMQP_URL_FORMAT;

class QueueApi {
    static void getInfo(ManagementApplication context, String vhost, String name, final BaseApi.ApiCallback<QueueInfo> callback) {
        BaseApi.getObject(context, callback, String.format(RequestPath.QUEUE_DETAIL, (vhost.equals(RequestPath.DEFAULT_VHOST) ? RequestPath.DEFAULT_VHOST_URL : vhost), name), QueueInfo.class);
    }

    static void delete(ManagementApplication context, String vhost, String name, final BaseApi.ApiCallback<Boolean> callback) {
        BaseApi.delete(context, callback, String.format(RequestPath.QUEUE_DELETE, (vhost.equals(RequestPath.DEFAULT_VHOST) ? RequestPath.DEFAULT_VHOST_URL : vhost), name));
    }

    static void purge(ManagementApplication context, String vhost, String name, final BaseApi.ApiCallback<Boolean> callback) {
        BaseApi.delete(context, callback, String.format(RequestPath.QUEUE_PURGE, (vhost.equals(RequestPath.DEFAULT_VHOST) ? RequestPath.DEFAULT_VHOST_URL : vhost), name));
    }

    static void moveMessages(ManagementApplication context, String vhost, String queue, String targetVhost, String targetQueue, final BaseApi.ApiCallback<Boolean> callback) {
        BaseApi.put(
                context,
                callback,
                String.format(RequestPath.QUEUE_MOVE, (vhost.equals(RequestPath.DEFAULT_VHOST) ? RequestPath.DEFAULT_VHOST_URL : vhost)),
                prepareMoveObject(vhost, queue, targetVhost, targetQueue)
        );
    }

    private static JSONObject prepareMoveObject(String vhost, String queue, String targetVhost, String targetQueue) {
        JSONObject result = new JSONObject();
        JSONObject value = new JSONObject();
        try {
            value.put("src-uri", String.format(AMQP_URL_FORMAT, (vhost.equals(RequestPath.DEFAULT_VHOST) ? RequestPath.DEFAULT_VHOST_URL : vhost)));
            value.put("src-queue", queue);
            value.put("dest-uri", String.format(AMQP_URL_FORMAT, (targetVhost.equals(RequestPath.DEFAULT_VHOST) ? RequestPath.DEFAULT_VHOST_URL : targetVhost)));
            value.put("dest-queue", targetQueue);
            value.put("prefetch-count", 1000);
            value.put("add-forward-headers", false);
            value.put("ack-mode", "on-confirm");
            value.put("delete-after", "queue-length");
            result.put("component", "shovel");
            result.put("vhost", vhost.equals(RequestPath.DEFAULT_VHOST) ? RequestPath.DEFAULT_VHOST_URL : vhost);
            result.put("name", String .format("Move from %s to %s", queue, targetQueue));
            result.put("value", value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
