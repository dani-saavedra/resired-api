package com.resired.api.admin.domain.repository;

import com.resired.api.admin.domain.entity.NotificationCategory;

import java.util.List;

public interface NotificationCategoryPort {

    void createNewNotificationCategory(NotificationCategory category);

    NotificationCategory getNotificationCategoryById(Integer id);

    List<NotificationCategory> getAllCategoriesByNeighborhoodId(Integer neighborhoodId);

    NotificationCategory getNotificationCategoryByIdAndNeighborhoodId(Integer id, Integer neighborhoodId);

}
