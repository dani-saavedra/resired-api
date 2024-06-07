package com.resired.api.shared.notification.domain.service;

import com.resired.api.shared.notification.domain.entity.Device;
import com.resired.api.shared.notification.domain.vo.NotificationMessage;

import java.util.List;

public interface NotificationSender {
    void sendToDevice(NotificationMessage notificationMessage, Device device);

    void sendToDeviceList(NotificationMessage notificationMessage, List<Device> devices);

    void sendToTopic(NotificationMessage notificationMessage, String topic);

    void subscribeDeviceToTopic(Device device, String topic);

    void unsubscribeDeviceToTopic(Device device, String topic);
}
