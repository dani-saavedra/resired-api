package com.resired.api.security.domain.repository;

import com.resired.api.security.domain.entity.User;

public interface UserPort {

    User getResidentByCredentials(String email, String password);

    void changePassword(String documentId, String newEncryptPass);
}
