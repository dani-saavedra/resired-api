package com.resired.api.shared.notification.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PushNotificationNeighborhood(String title, String message,
                                           @JsonProperty("neighborhood_id") Integer neighborhoodID) {
}
