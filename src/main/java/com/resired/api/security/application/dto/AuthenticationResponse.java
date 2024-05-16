package com.resired.api.security.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.resired.api.security.domain.entity.Rol;

import java.util.List;

public record AuthenticationResponse(@JsonProperty("access_token") String accessToken, List<Rol> roles, @JsonProperty("user_name") String userName,
                                     @JsonProperty("user_id") String userId, @JsonProperty("mandatory_change") Boolean mandatoryChange) {

}
