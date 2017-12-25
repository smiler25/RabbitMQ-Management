package com.smiler.rabbitadministration.overview;

import com.smiler.rabbitadministration.ManagementApplication;
import com.smiler.rabbitadministration.base.BaseViewModel;
import com.smiler.rabbitadministration.base.api.BaseApi;

public class OverviewViewModel extends BaseViewModel<Overview> {
    protected void loadData(ManagementApplication context) {
        OverviewApi.getInfo(context, new BaseApi.ApiCallback<Overview>() {
            @Override
            public void onResult(Overview result) {
                data.setValue(result);
            }

            @Override
            public void onError(String msg) {
                errorMessage.setValue(msg);
            }
        });
    }
}
