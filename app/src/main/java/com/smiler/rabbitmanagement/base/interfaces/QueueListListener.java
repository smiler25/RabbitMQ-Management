package com.smiler.rabbitmanagement.base.interfaces;

import com.smiler.rabbitmanagement.detail.QueueInfo;

public interface QueueListListener {
    void onListElementClick(QueueInfo queueInfo);
}
