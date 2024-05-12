package com.resired.api.security.domain.entity;

import com.resired.api.security.domain.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Rol {

    private UserType userType;
    private Long neighborhoodId;
    private String neighborhoodName;
    private Long homeId;
    private String homeName;

}
