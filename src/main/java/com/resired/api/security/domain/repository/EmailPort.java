package com.resired.api.security.domain.repository;

public interface EmailPort {

    void sendEmailToRecoverPass(String email, String token);

    void sendRegisteredResidentEmail(String email);
}
