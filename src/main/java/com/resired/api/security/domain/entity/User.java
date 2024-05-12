package com.resired.api.security.domain.entity;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class User {

    private final String userId;
    private final String userName;
    private final String userLastName;
    //private final String profile;
    private boolean active;
    boolean mandatoryChangePassword;

    public User(String userId, String userName, String userLastName, boolean active) {
        this.userId = userId;
        this.userName = userName;
        this.userLastName = userLastName;
        this.active = active;
    }

    public void validateMandatoryChangePassword(LocalDateTime update) {
        if (update == null) {
            mandatoryChangePassword = true;
        }
    }
}
