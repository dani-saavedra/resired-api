package com.resired.api.security.domain.repository;

public interface EmailPort {

    void sendEmailToRecoverPass(String email, String token);

    void sendRegisteredUserEmail(String email, String neighborhood, boolean isAdmin, String firstName);

    void sendAssociateNewUserToNeighborhood(String email, String neighborhood);
}
