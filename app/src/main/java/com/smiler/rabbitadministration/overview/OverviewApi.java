package com.smiler.rabbitadministration.overview;

import com.smiler.rabbitadministration.ManagementApplication;
import com.smiler.rabbitadministration.base.api.BaseApi;
import com.smiler.rabbitadministration.base.api.RequestPath;

class OverviewApi {
    static void getInfo(ManagementApplication context, final BaseApi.ApiCallback<Overview> callback) {
        BaseApi.requestObject(context, callback, RequestPath.OVERVIEW, Overview.class);
    }
}