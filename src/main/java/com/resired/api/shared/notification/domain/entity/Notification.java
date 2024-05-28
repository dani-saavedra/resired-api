package com.resired.api.shared.notification.domain.entity;

import lombok.Getter;

@Getter
public class Notification {
    private final Integer id;
    private final String message;
    private Boolean received;

    public Notification(Integer id, String message, Boolean received) {
        this.id = id;
        this.message = message;
        this.received = received;
    }
}
