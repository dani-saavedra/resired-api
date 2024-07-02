package com.resired.api.admin.domain.repository;

import com.resired.api.admin.domain.entity.NotificationCategory;
import com.resired.api.admin.domain.vo.LevelNotificationEnum;

public interface NotificationCategoryPort {

    void createNewNotificationCategory(Integer neighborhoodId, String name, LevelNotificationEnum levelNotification);

    NotificationCategory getNotificationCategoryById(Integer id);

    NotificationCategory getNotificationCategoryByNameAndNeighborhoodId(String name, Integer id);

}
