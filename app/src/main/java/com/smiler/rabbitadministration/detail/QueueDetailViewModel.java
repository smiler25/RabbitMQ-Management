package com.smiler.rabbitadministration.detail;

import com.smiler.rabbitadministration.ManagementApplication;
import com.smiler.rabbitadministration.base.BaseViewModel;
import com.smiler.rabbitadministration.base.api.BaseApi;

import lombok.Setter;

public class QueueDetailViewModel extends BaseViewModel<QueueInfo> {
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
