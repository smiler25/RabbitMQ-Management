package com.smiler.rabbitmanagement.overview;

import com.smiler.rabbitmanagement.ManagementApplication;
import com.smiler.rabbitmanagement.base.api.BaseApi;
import com.smiler.rabbitmanagement.base.api.RequestPath;

class OverviewApi {
    static void getInfo(ManagementApplication context, final BaseApi.ApiCallback<Overview> callback) {
        BaseApi.requestObject(context, callback, RequestPath.OVERVIEW, Overview.class);
    }
}