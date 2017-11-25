package com.smiler.rabbitmanagement.detail;

import com.smiler.rabbitmanagement.ManagementApplication;
import com.smiler.rabbitmanagement.base.BaseViewModel;
import com.smiler.rabbitmanagement.base.api.BaseApi;

import lombok.Setter;

class QueueDetailViewModel extends BaseViewModel<QueueInfo> {
    @Setter
    private String vhost;
    @Setter
    private String name;

    protected void loadData(ManagementApplication context) {
        QueueDetailApi.getInfo(context, vhost, name, new BaseApi.ApiCallback<QueueInfo>() {
            @Override
            public void onResult(QueueInfo result) {
                data.setValue(result);
            }

            @Override
            public void onError(String msg) {
                errorMessage.setValue(msg);
            }
        });
    }
}
