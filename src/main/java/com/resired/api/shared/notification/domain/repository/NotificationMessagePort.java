package com.resired.api.shared.notification.domain.repository;

import com.resired.api.shared.notification.domain.vo.NotificationMessage;

import java.util.List;

public interface NotificationMessagePort {
    List<NotificationMessage> getAllNotificationMessagesByEmail(String email);

    NotificationMessage getLastNotification(String email);

    void markAllNotificationsAsRead(String email);

    void saveNotificationForHomeResidents(NotificationMessage notificationMessage, Integer homeId,
                                          Integer neighborhoodId);

    void saveNotificationForNeighborhoodResidents(NotificationMessage notificationMessage,
                                                  Integer neighborhoodId);

    List<NotificationMessage> getAllNotificationMessagesByNeighborhoodId(Integer neighborhoodId);
}
