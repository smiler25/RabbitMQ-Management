package com.smiler.rabbitmanagement.base.interfaces;

public interface UpdatableFragment {
    void updateData();
    void setListener(FragmentListListener listener);
    void setCallback(UpdatableFragmentListener listener);
}
