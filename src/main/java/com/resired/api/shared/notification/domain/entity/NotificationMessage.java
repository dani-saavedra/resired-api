package com.resired.api.shared.notification.domain.entity;

import com.resired.api.admin.domain.entity.NotificationCategory;
import com.resired.api.admin.domain.vo.LevelNotificationEnum;

public record NotificationMessage(Integer id, String title, String message, String date, NotificationCategory category,
                                  LevelNotificationEnum level) {
}
