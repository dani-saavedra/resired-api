package com.resired.api.security.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.resired.api.security.domain.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Rol {

    @JsonProperty("user_type")
    private UserType userType;
    @JsonProperty("neighborhood_id")
    private Long neighborhoodId;
    @JsonProperty("neighborhood_name")
    private String neighborhoodName;
    @JsonProperty("home_id")
    private Long homeId;
    @JsonProperty("home_name")
    private String homeName;

}
