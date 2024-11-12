package com.resired.api.shared.notification.domain.service;

import com.resired.api.shared.notification.domain.entity.Device;
import com.resired.api.shared.notification.domain.entity.PushNotification;

import java.util.List;

public interface NotificationSender {

    void sendToDevice(PushNotification notificationMessage, Device device);

    void sendToDeviceList(PushNotification notificationMessage, List<Device> devices);

    void sendToTopic(PushNotification notificationMessage, String topic);
}
