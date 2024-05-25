package com.resired.api.security.domain.repository;

import com.resired.api.security.domain.entity.User;

public interface UserPort {

    User getUserByCredentials(String email, String password);

    void changePassword(String documentId, String newEncryptPass);

    User getResidentByEmail(String email);

    User getGuardByEmail(String email);
}
