package com.smiler.rabbitadministration.base;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.smiler.rabbitadministration.ManagementApplication;
import com.smiler.rabbitadministration.common.ActionInfo;

public abstract class BaseViewModel<T> extends ViewModel {
    protected MutableLiveData<T> data;
    protected MutableLiveData<String> errorMessage;
    protected MutableLiveData<ActionInfo> action;

    public MutableLiveData<T> getModel() {
        if (data == null) {
            data = new MutableLiveData<>();
        }
        return data;
    }

    public MutableLiveData<String> getError() {
        if (errorMessage == null) {
            errorMessage = new MutableLiveData<>();
        }
        return errorMessage;
    }

    public MutableLiveData<ActionInfo> getAction() {
        if (action == null) {
            action = new MutableLiveData<>();
        }
        return action;
    }

    protected abstract void loadData(ManagementApplication context);
}