package com.resired.api.security.domain.repository;

public interface EmailNotificationPort {

    void sendEmailNotification(String email, String subject, String body);
}
