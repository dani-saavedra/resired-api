package com.resired.api.security.application.dto;

import com.resired.api.security.domain.entity.Rol;

import java.util.List;

public record AuthenticationResponse(String token, List<Rol> roles, String userName,
                                     String userId, Boolean mandatoryChange) {

}
