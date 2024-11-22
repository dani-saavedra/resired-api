package com.resired.api.security.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.resired.api.security.domain.entity.Rol;

import java.util.List;

public record AuthenticationAdminResponse(@JsonProperty("access_token") String accessToken,
                                          @JsonProperty("refresh_token") String refreshToken,
                                          @JsonProperty("roles") List<Rol> roles,
                                          @JsonProperty("user_name") String userName,
                                          @JsonProperty("user_email") String userEmail,
                                          @JsonProperty("document_id") String documentId,
                                          @JsonProperty("mandatory_change") Boolean mandatoryChange) {

}
