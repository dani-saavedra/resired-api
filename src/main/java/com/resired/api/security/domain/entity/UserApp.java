package com.resired.api.security.domain.entity;

import io.jsonwebtoken.Claims;


public record UserApp(Integer userId, String email, String rol, Integer neighborhoodId
    , Integer homeId) {

    public static UserApp generateUserAppFromClaims(Claims claims) {
        return new UserApp((Integer) claims.get("userId"), claims.getSubject(), (String) claims.get("rol"),
            (Integer) claims.get("neighborhoodId"), (Integer) claims.get("homeId"));
    }
}
