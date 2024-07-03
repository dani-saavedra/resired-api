package com.resired.api.shared.notification.domain.exception;

public class NotificationCategoryNotFoundException extends RuntimeException {
    public NotificationCategoryNotFoundException(Integer categoryId) {
        super("Notification category with ID " + categoryId +
            " not found");
    }
}
