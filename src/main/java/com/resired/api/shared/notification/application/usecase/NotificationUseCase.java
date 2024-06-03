package com.resired.api.shared.notification.application.usecase;

import com.resired.api.shared.notification.application.dto.NewNotificationsResponse;
import com.resired.api.shared.notification.application.dto.NotificationHomeRequest;
import com.resired.api.shared.notification.application.dto.NotificationNeighborhoodRequest;
import com.resired.api.shared.notification.domain.entity.Device;
import com.resired.api.shared.notification.domain.repository.DevicePort;
import com.resired.api.shared.notification.domain.repository.NotificationMessagePort;
import com.resired.api.shared.notification.domain.service.NotificationSender;
import com.resired.api.shared.notification.domain.vo.NotificationMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificationUseCase {
    private final NotificationSender notificationSenderService;
    private final DevicePort deviceRepository;
    private final NotificationMessagePort notificationRepository;

    public void notifyHome(NotificationHomeRequest requestDTO) {
        NotificationMessage notificationMessage = new NotificationMessage(requestDTO.title(),
            requestDTO.message(), false);

        List<Device> devices = deviceRepository.getDevicesFromHomeOwner(requestDTO.homeID());

        if (devices.size() > 1) {
            notificationSenderService.sendToDeviceList(notificationMessage, devices);
            return;
        }

        notificationSenderService.sendToDevice(notificationMessage,
            devices.get(0));

        notificationRepository.saveNotificationForHome(notificationMessage, requestDTO.homeID());
    }

    public void notifyNeighborhood(NotificationNeighborhoodRequest requestDTO) {
        String topic = "/topics/neighborhoods/" + requestDTO.neighborhoodID();

        NotificationMessage notificationMessage = new NotificationMessage(requestDTO.title(),
            requestDTO.message(), false);

        notificationSenderService.sendToTopic(notificationMessage, topic);

        notificationRepository.saveNotificationForNeighborhood(notificationMessage,
            requestDTO.neighborhoodID());
    }

    public List<NotificationMessage> listAllNotifications(String email) {
        List<NotificationMessage> notifications = notificationRepository.getAllNotificationMessagesByEmail(email);
        notificationRepository.markAllNotificationsAsRead(email);
        return notifications;
    }

    public NewNotificationsResponse thereAreNewNotifications(String email) {
        NotificationMessage notificationMessage = notificationRepository.getLastNotification(email);
        boolean newNotification;
        if (notificationMessage == null) {
            newNotification = false;
        } else {
            newNotification = !notificationMessage.viewed();
        }

        return new NewNotificationsResponse(newNotification);
    }
}
