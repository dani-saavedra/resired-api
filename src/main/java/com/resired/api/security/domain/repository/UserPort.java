package com.resired.api.security.domain.repository;

import com.resired.api.security.domain.entity.User;

import java.util.List;

public interface UserPort {

    User getUserByCredentials(String email, String password);

    void changePassword(String documentId, String newEncryptPass);

    void changePassword(Integer userId, String newEncryptPass);

    User getResidentByEmail(String email);

    User getGuardByEmail(String email);

    List<User> findResidentsByHomeId(Integer homeId);

    User getUserById(Integer userId);
}
