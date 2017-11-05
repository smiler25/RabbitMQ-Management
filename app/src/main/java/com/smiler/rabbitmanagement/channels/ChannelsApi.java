package com.smiler.rabbitmanagement.channels;

import com.smiler.rabbitmanagement.ManagementApplication;
import com.smiler.rabbitmanagement.base.api.BaseApi;
import com.smiler.rabbitmanagement.base.api.RequestPath;

import java.util.ArrayList;

class ChannelsApi {
    static void getList(ManagementApplication context, final BaseApi.ApiCallback<ArrayList<Channel>> callback) {
        BaseApi.requestList(context, callback, RequestPath.CHANNELS, Channel.class);
    }
}