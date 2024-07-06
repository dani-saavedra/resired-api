package com.resired.api.shared.notification.application.usecase;

import com.resired.api.admin.domain.entity.NotificationCategory;
import com.resired.api.admin.domain.repository.NotificationCategoryPort;
import com.resired.api.shared.notification.application.dto.*;
import com.resired.api.shared.notification.domain.entity.Device;
import com.resired.api.shared.notification.domain.entity.NotificationMessage;
import com.resired.api.shared.notification.domain.entity.PushNotification;
import com.resired.api.shared.notification.domain.exception.NotificationCategoryNotFoundException;
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
    private final NotificationCategoryPort notificationCategoryRepository;
    @Value("${topic.neighborhood}")
    private String NEIGHBORHOOD_TOPIC;

    public void notifyHome(NotificationHomeRequest requestDTO) {
        NotificationCategory category = notificationCategoryRepository.getNotificationCategoryById(requestDTO.categoryId());

        if (category == null) throw new NotificationCategoryNotFoundException(requestDTO.categoryId());

        List<Device> devices = deviceRepository.getDevicesForHomeResident(requestDTO.homeID());

        PushNotification notification = new PushNotification(requestDTO.title(), requestDTO.message());

        if (!devices.isEmpty()) {
            notificationSenderService.sendToDeviceList(notification, devices);
        }
    }

    public void notifyHome(NotificationHome notification) {
        PushNotification notificationMessage = new PushNotification(notification.title(),
            notification.message());
        List<Device> devices = deviceRepository.getDevicesForHomeResident(notification.homeID());
        if (!devices.isEmpty()) {
            notificationSenderService.sendToDeviceList(notificationMessage, devices);
        }
    }

    public void notifyResident(NotificationResident notification) {
        PushNotification notificationMessage = new PushNotification(notification.title(),
            notification.message());
        Device device = deviceRepository.getDeviceByUser(notification.userId());
        if (device != null) {
            notificationSenderService.sendToDevice(notificationMessage, device);
        }
    }

    public void notifyNeighborhood(NotificationNeighborhoodRequest requestDTO) {
        String topic = NEIGHBORHOOD_TOPIC + requestDTO.neighborhoodID();

        NotificationCategory category = notificationCategoryRepository.getNotificationCategoryById(requestDTO.categoryId());

        if (category == null) throw new NotificationCategoryNotFoundException(requestDTO.categoryId());

        NotificationMessage notificationMessage = new NotificationMessage(null, requestDTO.title(),
            requestDTO.message(), null, category, requestDTO.level());

        PushNotification notification = new PushNotification(requestDTO.title(), requestDTO.message());

        notificationSenderService.sendToTopic(notification, topic);

        notificationRepository.saveNotificationForNeighborhoodResidents(notificationMessage,
            requestDTO.neighborhoodID());
    }

    public void notifyNeighborhood(PushNotificationNeighborhood requestDTO) {
        String topic = NEIGHBORHOOD_TOPIC + requestDTO.neighborhoodID();

        PushNotification notificationMessage = new PushNotification(requestDTO.title(),
            requestDTO.message());

        notificationSenderService.sendToTopic(notificationMessage, topic);
    }

    public List<NotificationForUserDto> listAllNotifications(String email) {
        List<NotificationForUserDto> notifications = notificationRepository.getAllNotificationMessagesByEmail(email)
            .stream()
            .map(notificationForUser -> new NotificationForUserDto(
                notificationForUser.notificationMessage().id(),
                notificationForUser.notificationMessage().title(),
                notificationForUser.notificationMessage().message(),
                notificationForUser.notificationMessage().category().name(),
                notificationForUser.notificationMessage().level(),
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
