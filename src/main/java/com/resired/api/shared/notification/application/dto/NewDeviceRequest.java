package com.resired.api.shared.notification.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NewDeviceRequest(@JsonProperty("allow_notifications") Boolean allowNotifications,
                               @JsonProperty("device_id") String deviceID) {
}
