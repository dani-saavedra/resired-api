package com.resired.api.shared.notification.application.usecase;

import com.resired.api.shared.notification.application.dto.*;
import com.resired.api.shared.notification.domain.entity.Device;
import com.resired.api.shared.notification.domain.entity.NotificationMessage;
import com.resired.api.shared.notification.domain.repository.DevicePort;
import com.resired.api.shared.notification.domain.repository.NotificationMessagePort;
import com.resired.api.shared.notification.domain.repository.UserNotificationPort;
import com.resired.api.shared.notification.domain.service.NotificationSender;
import com.resired.api.shared.notification.domain.vo.NotificationForUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationUseCase {
    private final NotificationSender notificationSenderService;
    private final DevicePort deviceRepository;
    private final NotificationMessagePort notificationRepository;
    private final UserNotificationPort userNotificationRepository;
    @Value("${topic.neighborhood}")
    private String NEIGHBORHOOD_TOPIC;

    public void notifyHome(NotificationHomeRequest requestDTO) {
        NotificationMessage notificationMessage = new NotificationMessage(null, requestDTO.title(),
            requestDTO.message(), null);

        List<Device> devices = deviceRepository.getDevicesForHomeResident(requestDTO.homeID());

        if (!devices.isEmpty()) {
            notificationSenderService.sendToDeviceList(notificationMessage, devices);
            notificationRepository.saveNotificationForHomeResidents(notificationMessage,
                requestDTO.homeID(), requestDTO.neighborhoodID());
        }
    }

    public void notifyHome(NotificationHome notification) {
        NotificationMessage notificationMessage = new NotificationMessage(null, notification.title(),
            notification.message(), null);
        List<Device> devices = deviceRepository.getDevicesForHomeResident(notification.homeID());
        if (!devices.isEmpty()) {
            notificationSenderService.sendToDeviceList(notificationMessage, devices);
        }
    }

    public void notifyNeighborhood(NotificationNeighborhoodRequest requestDTO) {
        String topic = NEIGHBORHOOD_TOPIC + requestDTO.neighborhoodID();

        NotificationMessage notificationMessage = new NotificationMessage(null, requestDTO.title(),
            requestDTO.message(), null);

        notificationSenderService.sendToTopic(notificationMessage, topic);

        notificationRepository.saveNotificationForNeighborhoodResidents(notificationMessage,
            requestDTO.neighborhoodID());
    }

    public List<NotificationForUserDto> listAllNotifications(String email) {
        List<NotificationForUserDto> notifications = notificationRepository.getAllNotificationMessagesByEmail(email)
            .stream()
            .map(notificationForUser -> new NotificationForUserDto(
                notificationForUser.notificationMessage().id(),
                notificationForUser.notificationMessage().title(),
                notificationForUser.notificationMessage().message(),
                notificationForUser.notificationMessage().date(),
                notificationForUser.viewed()
            )).toList();
        notificationRepository.markAllNotificationsAsRead(email);
        return notifications;
    }

    public NewNotificationsResponse thereAreNewNotifications(String email) {
        NotificationForUser notificationMessage = notificationRepository.getLastNotification(email);
        boolean newNotification;
        if (notificationMessage == null) {
            newNotification = false;
        } else {
            newNotification = !notificationMessage.viewed();
        }

        return new NewNotificationsResponse(newNotification);
    }

    public void deleteNotificationForUser(Integer notificationId, String email) {
        userNotificationRepository.deleteNotificationByID(email, notificationId);
    }

    public List<NotificationMessage> getAllNotificationsByNeighborhoodId(Integer neighborhoodId) {
        return notificationRepository.getAllNotificationMessagesByNeighborhoodId(neighborhoodId);
    }
}
