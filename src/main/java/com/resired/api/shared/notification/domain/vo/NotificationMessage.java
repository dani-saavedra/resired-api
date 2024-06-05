package com.resired.api.shared.notification.domain.vo;

public record NotificationMessage(Integer id, String title, String message, Boolean viewed) {
    public NotificationMessage(String title, String message, Boolean viewed) {
        this(0, title, message, viewed);
    }

    ;

}
