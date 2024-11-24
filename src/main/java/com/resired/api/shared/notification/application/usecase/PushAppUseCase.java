package com.resired.api.shared.notification.application.usecase;

import com.resired.api.shared.notification.application.dto.NotificationBlockRequest;
import com.resired.api.shared.notification.application.dto.NotificationHomeRequest;
import com.resired.api.shared.notification.application.dto.NotificationNeighborhoodRequest;
import com.resired.api.shared.notification.application.dto.NotificationResident;
import com.resired.api.shared.notification.domain.entity.Device;
import com.resired.api.shared.notification.domain.entity.PushNotification;
import com.resired.api.shared.notification.domain.port.PushNotificationPort;
import com.resired.api.shared.notification.domain.repository.DeviceQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PushAppUseCase {

    private final PushNotificationPort pushNotificationPortService;
    private final DeviceQuery deviceQuery;

    @Value("${topic.neighborhood}")
    private String neighborhoodTopic;

    @Value("${topic.block}")
    private String blockTopic;

    public void notifyHome(NotificationHomeRequest requestDTO) {
        List<Device> devices = deviceQuery.getDevicesForHomeResident(requestDTO.homeID());

        if (!devices.isEmpty()) {
            PushNotification notification = new PushNotification(requestDTO.title(), requestDTO.message());
            pushNotificationPortService.sendToDeviceList(notification, devices);
        }
    }

    public void notifyResident(NotificationResident notification) {
        List<Device> devices = deviceQuery.getDevicesByUser(notification.userId());
        if (devices != null && !devices.isEmpty()) {
            devices.forEach(device -> {
                PushNotification notificationMessage = new PushNotification(notification.title(),
                    notification.message());
                pushNotificationPortService.sendToDevice(notificationMessage, device);
            });
        }
    }

    public void notifyNeighborhood(NotificationNeighborhoodRequest requestDTO) {
        String topic = neighborhoodTopic + requestDTO.neighborhoodID();
        PushNotification notification = new PushNotification(requestDTO.title(), requestDTO.message());
        pushNotificationPortService.sendToTopic(notification, topic);
    }

    public void notifyBlock(NotificationBlockRequest requestDTO) {
        String topic = blockTopic + requestDTO.blockID();
        PushNotification notification = new PushNotification(requestDTO.title(), requestDTO.message());
        pushNotificationPortService.sendToTopic(notification, topic);
    }
}
