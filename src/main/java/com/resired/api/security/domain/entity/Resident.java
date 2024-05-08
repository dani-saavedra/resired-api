package com.resired.api.security.domain.entity;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class Resident {

    private String userId;
    private String userName;
    private String userLastName;
    private String profile;
    private boolean active;
    boolean mandatoryChangePassword;

    public Resident(String userId, String userName, String userLastName, boolean active) {
        this.userId = userId;
        this.userName = userName;
        this.userLastName = userLastName;
        this.active = active;
        this.profile = "Resident";
    }

    public void validateMandatoryChangePassword(LocalDateTime update) {
        if (update == null) {
            mandatoryChangePassword = true;
        }
    }
}
