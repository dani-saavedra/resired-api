package com.resired.api.security.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ResetPasswordRequest(
    String email, @JsonProperty("old") String oldPassword, @JsonProperty("new") String newPassword) {
}
