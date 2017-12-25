package com.smiler.rabbitadministration.channels;

import com.smiler.rabbitadministration.ManagementApplication;
import com.smiler.rabbitadministration.base.api.BaseApi;
import com.smiler.rabbitadministration.base.api.RequestPath;

import java.util.ArrayList;

class ChannelsApi {
    static void getList(ManagementApplication context, final BaseApi.ApiCallback<ArrayList<Channel>> callback) {
        BaseApi.requestList(context, callback, RequestPath.CHANNELS, Channel.class);
    }
}