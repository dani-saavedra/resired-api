package com.resired.api.security.application.dto;

public record AuthenticationResponse(String token, String profile, String userName,
                                     String userId, Boolean mandatoryChange) {

}
