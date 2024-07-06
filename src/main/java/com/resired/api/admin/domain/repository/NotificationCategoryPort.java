package com.resired.api.admin.domain.repository;

import com.resired.api.admin.domain.entity.NotificationCategory;
import com.resired.api.admin.domain.vo.LevelNotificationEnum;

import java.util.List;

public interface NotificationCategoryPort {

    void createNewNotificationCategory(Integer neighborhoodId, String name, LevelNotificationEnum levelNotification);

    NotificationCategory getNotificationCategoryById(Integer id);

    NotificationCategory getNotificationCategoryByNameAndNeighborhoodId(String name, Integer id);

    List<NotificationCategory> getAllCategoriesByNeighborhoodId(Integer id);

    NotificationCategory getNotificationCategoryByIdAndNeighborhoodId(Integer id, Integer neighborhoodId);

}
