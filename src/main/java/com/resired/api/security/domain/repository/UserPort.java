package com.resired.api.security.domain.repository;

import com.resired.api.security.domain.entity.User;
import com.resired.api.security.domain.enums.UserType;

import java.util.List;

public interface UserPort {

    User getUserByCredentials(String email, String password);

    void changePassword(String documentId, String newEncryptPass);

    void changePassword(Integer userId, String newEncryptPass);

    User getUserByEmailAndType(String email, UserType roleFilter);

    List<User> findResidentsByHomeId(Integer homeId);

    User getUserById(Integer userId);
}
