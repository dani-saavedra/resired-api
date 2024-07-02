package com.resired.api.shared.notification.domain.vo;

import com.resired.api.shared.notification.domain.entity.NotificationMessage;

public record NotificationForUser(NotificationMessage notificationMessage, Boolean viewed) {
}
