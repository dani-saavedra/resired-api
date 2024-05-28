package com.resired.api.shared.notification.domain.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserNotification {
    private final String email;

    private List<Device> devices;

    public UserNotification(String email, List<Device> devices) {
        this.email = email;
        this.devices = devices;
    }
}
