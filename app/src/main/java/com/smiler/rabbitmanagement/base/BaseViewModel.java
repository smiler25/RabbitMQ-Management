package com.smiler.rabbitmanagement.base;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.smiler.rabbitmanagement.ManagementApplication;

public abstract class BaseViewModel<T> extends ViewModel {
    protected MutableLiveData<T> data;

    public MutableLiveData<T> getModel() {
        if (data == null) {
            data = new MutableLiveData<>();
        }
        return data;
    }

    protected abstract void loadData(ManagementApplication context);
}