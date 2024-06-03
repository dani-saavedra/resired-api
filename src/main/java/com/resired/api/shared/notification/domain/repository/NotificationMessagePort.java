package com.resired.api.shared.notification.domain.repository;

import com.resired.api.shared.notification.domain.vo.NotificationMessage;

import java.util.List;

public interface NotificationMessagePort {
    List<NotificationMessage> getAllNotificationMessagesByEmail(String email);

    NotificationMessage getLastNotification(String email);

    void markAllNotificationsAsRead(String email);

    void saveNotificationForHome(NotificationMessage notificationMessage, Integer homeId);

    void saveNotificationForNeighborhood(NotificationMessage notificationMessage,
                                         Integer neighborhoodId);
    
}
