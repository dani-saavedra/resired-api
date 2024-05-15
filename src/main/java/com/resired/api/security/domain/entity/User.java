package com.resired.api.security.domain.entity;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;

@Getter
public class User {

    private final String userId;
    private final String userName;
    private final String email;
    private final String userLastName;
    private final List<Rol> roles;
    private final boolean active;
    boolean mandatoryChangePassword;

    public User(String userId, String userName, String email, String userLastName, boolean active, List<Rol> roles) {
        this.userId = userId;
        this.userName = userName;
        this.userLastName = userLastName;
        this.active = active;
        this.roles = roles;
        this.email = email;
    }

    public void validateMandatoryChangePassword(LocalDateTime update) {
        if (update == null) {
            mandatoryChangePassword = true;
        }
    }
}
