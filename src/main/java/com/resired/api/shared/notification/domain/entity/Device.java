package com.resired.api.shared.notification.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;


public record Device(@JsonProperty("device_id") String id) {
}
