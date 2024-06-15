package com.resired.api.security.domain.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.resired.api.security.domain.enums.UserType;
import lombok.Getter;

@Getter
public class User {

    private final Integer id;
    private final String documentId;
    private final String userName;
    private final String email;
    private final String userLastName;
    private final List<Rol> roles;
    private final boolean active;
    boolean mandatoryChangePassword;

    public User(Integer id, String documentId, String userName, String email, String userLastName, boolean active, List<Rol> roles) {
        this.id = id;
        this.documentId = documentId;
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

    public boolean hasRole(UserType rol) {
        for (Rol role : roles) {
            if (role.getUserType().equals(rol)) {
                return true;
            }
        }
        return false;
    }

    public String getFullNameLastOneFirst() {
        return this.userLastName + " " + this.userName;
    }
}
