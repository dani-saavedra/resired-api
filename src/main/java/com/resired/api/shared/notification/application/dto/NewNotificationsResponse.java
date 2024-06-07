package com.resired.api.shared.notification.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NewNotificationsResponse(@JsonProperty("new_notifications") Boolean newNotifications) {
}
