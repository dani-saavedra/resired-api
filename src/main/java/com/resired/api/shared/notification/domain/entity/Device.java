package com.resired.api.shared.notification.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Device {
    private final String id;
    private Boolean allowNotifications;
}
