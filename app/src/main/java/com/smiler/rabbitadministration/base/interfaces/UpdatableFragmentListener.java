package com.smiler.rabbitadministration.base.interfaces;

import com.smiler.rabbitadministration.common.ActionTypes;

public interface UpdatableFragmentListener {
    void startLoading();
    void stopLoading();
    void handleAction(ActionTypes action);
}
