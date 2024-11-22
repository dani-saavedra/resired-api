package com.resired.api.shared.notification.domain.port;

import com.resired.api.shared.notification.domain.entity.Device;
import com.resired.api.shared.notification.domain.entity.PushNotification;

import java.util.List;

public interface PushNotificationPort {

    void sendToDevice(PushNotification notificationMessage, Device device);

    void sendToDeviceList(PushNotification notificationMessage, List<Device> devices);

    void sendToTopic(PushNotification notificationMessage, String topic);
}
