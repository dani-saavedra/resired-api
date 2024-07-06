package com.resired.api.shared.notification.domain.repository;

import com.resired.api.shared.notification.domain.entity.NotificationMessage;
import com.resired.api.shared.notification.domain.vo.NotificationForUser;

import java.util.List;

public interface NotificationMessagePort {
    List<NotificationForUser> getAllNotificationMessagesByEmail(String email);

    NotificationForUser getLastNotification(String email);

    void markAllNotificationsAsRead(String email);

    void saveNotificationForHomeResidents(NotificationMessage notificationMessage, Integer homeId);

    void saveNotificationForNeighborhoodResidents(NotificationMessage notificationMessage,
                                                  Integer neighborhoodId);

    List<NotificationMessage> getAllNotificationMessagesByNeighborhoodId(Integer neighborhoodId);
}
