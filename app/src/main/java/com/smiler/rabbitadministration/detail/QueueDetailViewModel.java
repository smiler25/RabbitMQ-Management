package com.smiler.rabbitadministration.detail;

import com.smiler.rabbitadministration.ManagementApplication;
import com.smiler.rabbitadministration.base.BaseViewModel;
import com.smiler.rabbitadministration.base.api.BaseApi;
import com.smiler.rabbitadministration.common.ActionInfo;
import com.smiler.rabbitadministration.common.ActionTypes;

import lombok.Getter;
import lombok.Setter;

public class QueueDetailViewModel extends BaseViewModel<QueueInfo> {
    @Setter
    private String vhost;
    @Setter @Getter
    private String name;

    protected void loadData(ManagementApplication context) {
        QueueApi.getInfo(context, vhost, name, new BaseApi.ApiCallback<QueueInfo>() {
            @Override
            public void onResult(QueueInfo result) {
                name = result.getName();
                vhost = result.getVhost();
                data.setValue(result);
            }

            @Override
            public void onError(String msg) {
                errorMessage.setValue(msg);
            }
        });
    }

    public void setData(QueueInfo data) {
        name = data.getName();
        vhost = data.getVhost();
    }

    void purge(ManagementApplication context) {
        QueueApi.purge(context, vhost, name, new BaseApi.ApiCallback<Boolean>() {
            @Override
            public void onResult(Boolean result) {
                action.setValue(new ActionInfo(ActionTypes.QUEUE_PURGE, name));
            }
            @Override
            public void onError(String msg) {
                        errorMessage.setValue(msg);
                    }
        });
    }

    void delete(ManagementApplication context) {
        QueueApi.delete(context, vhost, name, new BaseApi.ApiCallback<Boolean>() {
            @Override
            public void onResult(Boolean result) {
                action.setValue(new ActionInfo(ActionTypes.QUEUE_DELETE, name));
            }
            @Override
            public void onError(String msg) {errorMessage.setValue(msg);}
        });
    }

    void move(ManagementApplication context, String targetName) {
        QueueApi.moveMessages(context, vhost, name, vhost, targetName, new BaseApi.ApiCallback<Boolean>() {
            @Override
            public void onResult(Boolean result) {
                action.setValue(new ActionInfo(ActionTypes.QUEUE_MOVE, String.format("%s -> %s", name, targetName)));
            }
            @Override
            public void onError(String msg) {
                errorMessage.setValue(msg);
            }
        });
    }
}