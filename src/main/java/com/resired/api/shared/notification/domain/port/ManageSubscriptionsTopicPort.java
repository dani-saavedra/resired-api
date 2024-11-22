package com.resired.api.shared.notification.domain.port;

import com.resired.api.shared.notification.domain.entity.Device;

public interface ManageSubscriptionsTopicPort {
    void subscribeDeviceToTopic(Device device, String topic);

    void unsubscribeDeviceToTopic(Device device, String topic);
}
