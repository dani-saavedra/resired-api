package com.resired.api.shared.notification.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NotificationHomeRequest(String title, String message, @JsonProperty("home_id") Integer homeID) {
}
