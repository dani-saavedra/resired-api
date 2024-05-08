package com.resired.api.security.domain.entity;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Resident {

    String userId;
    String userName;
    String userLastName;
    String profile;

    boolean mandatoryChangePassword;

    public Resident(String userId, String userName, String userLastName) {
        this.userId = userId;
        this.userName = userName;
        this.userLastName = userLastName;
        this.profile = "Resident";
    }

    public void validateMandatoryChangePassword(LocalDateTime update) {
        if (update == null) {
            mandatoryChangePassword = true;
        }
    }
}
