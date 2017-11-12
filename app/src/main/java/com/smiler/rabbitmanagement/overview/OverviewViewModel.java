package com.smiler.rabbitmanagement.overview;

import com.smiler.rabbitmanagement.ManagementApplication;
import com.smiler.rabbitmanagement.base.BaseViewModel;
import com.smiler.rabbitmanagement.base.api.BaseApi;

class OverviewViewModel extends BaseViewModel<Overview> {
    protected void loadData(ManagementApplication context) {
        OverviewApi.getInfo(context, new BaseApi.ApiCallback<Overview>() {
            @Override
            public void onResult(Overview result) {
                data.setValue(result);
            }

            @Override
            public void onError(String msg) {
//                Toast.makeText(context, String.format(getString(R.string.api_error_overview), msg), Toast.LENGTH_LONG).show();
            }
        });
    }
}
