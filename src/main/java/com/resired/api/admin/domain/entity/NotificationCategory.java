package com.resired.api.admin.domain.entity;

import com.resired.api.admin.domain.vo.LevelNotificationEnum;

public record NotificationCategory(Integer id, Integer neighborhoodId, String name, String defaultMessage,
                                   LevelNotificationEnum level, String defaultTitle) {
}
