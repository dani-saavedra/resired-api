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
    private Integer neighborhoodId;
    @JsonProperty("neighborhood_name")
    private String neighborhoodName;
    @JsonProperty("home_id")
    private Integer homeId;
    @JsonProperty("home_name")
    private String homeName;

    public Rol(UserType userType, Integer neighborhoodId, String neighborhoodName) {
        this.userType = userType;
        this.neighborhoodId = neighborhoodId;
        this.neighborhoodName = neighborhoodName;
    }
}
