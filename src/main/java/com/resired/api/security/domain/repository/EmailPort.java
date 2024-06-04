package com.resired.api.security.domain.repository;

public interface EmailPort {

    void sendEmailToRecoverPass(String email, String token);

    void sendRegisteredUserEmail(String email);

    void sendAssociateNewUserToNeighborhood(String email);
}
