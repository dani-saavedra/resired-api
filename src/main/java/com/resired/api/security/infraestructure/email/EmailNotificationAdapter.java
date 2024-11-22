package com.resired.api.security.infraestructure.email;

import com.resired.api.security.domain.repository.EmailNotificationPort;
import com.resired.api.security.domain.repository.EmailPort;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class EmailNotificationAdapter implements EmailNotificationPort {


    private final Session sessionEmail;

    @Value("${smtp.user}")
    private String smtpUser;

    @Override
    public void sendEmailNotification(String email, String subject, String body) {
        try {
            Message message = new MimeMessage(sessionEmail);
            message.setFrom(new InternetAddress(smtpUser));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
        } catch (MessagingException e) {
            log.error("Error sending email", e);
        }
    }
}
