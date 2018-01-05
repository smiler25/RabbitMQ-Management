package com.smiler.rabbitadministration.base.interfaces;

import com.smiler.rabbitadministration.common.ActionInfo;

public interface UpdatableFragmentListener {
    void startLoading();
    void stopLoading();
    void handleAction(ActionInfo actionInfo);
}