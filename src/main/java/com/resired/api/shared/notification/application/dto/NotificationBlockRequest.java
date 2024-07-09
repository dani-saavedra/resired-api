package com.resired.api.shared.notification.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record NotificationBlockRequest(String title, String message,
                                       @JsonProperty("block_id") Integer blockID) {
}
