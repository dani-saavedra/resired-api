package com.resired.api.shared.notification.domain.service;

import com.resired.api.shared.notification.domain.entity.Device;

public interface DeviceNotificationManagement {
    void subscribeDeviceToTopic(Device device, String topic);

    void unsubscribeDeviceToTopic(Device device, String topic);
}
