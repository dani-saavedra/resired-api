package com.resired.api.security.application.dto;

public record RecoveryPasswordRequest(String token, String password) {
}
