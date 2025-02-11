package com.resired.api.security.domain.repository;

public interface EmailPort {

    void sendEmailToRecoverPass(String email, String token, String name);

    void sendRegisteredUserEmail(String email, String neighborhood, boolean isAdmin, String firstName);

    void sendAssociateNewUserToNeighborhood(String email, String neighborhood, String firstName);
}
