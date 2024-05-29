package com.resired.api.security.domain.repository;

public interface RecoveryPassPort {

    void sendEmailWithToken(String email, String token);
}
