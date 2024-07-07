package com.resired.api.shared.notification.domain.repository;

import com.resired.api.shared.notification.domain.entity.NotificationMessage;

import java.util.List;

public interface NotificationMessagePort {

    List<NotificationMessage> getAllNotificationMessagesByNeighborhoodId(Integer neighborhoodId);
}
