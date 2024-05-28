package com.resired.api.shared.notification.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Device {
    @JsonProperty("device_id")
    private final String id;

    @JsonProperty("allow_notifications")
    private Boolean allowNotifications;
}
