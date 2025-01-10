package com.resired.api.security.infraestructure.email;

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
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
@RequiredArgsConstructor
@Slf4j
public class JavaSmtpHostingerSenderService implements EmailPort {


    private final Session sessionEmail;
    private final TemplateEngine templateEngine;

    @Value("${smtp.user}")
    private String smtpUser;

    @Override
    public void sendEmailToRecoverPass(String email, String token, String name) {
        Context context = new Context();

        context.setVariable("name", name);
        context.setVariable("email", email);
        context.setVariable("token", token);

        String body = templateEngine.process("reset-password", context);

        try {
            Message message = new MimeMessage(sessionEmail);
            message.setFrom(new InternetAddress(smtpUser));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Restablecimiento de contraseña");
            message.setContent(body, "text/html; charset=utf-8");

            Transport.send(message);

        } catch (MessagingException e) {
            log.error("Error sending email", e);
        }
    }

    @Override
    public void sendRegisteredUserEmail(String email, String neighborhood, boolean isAdmin, String firstName) {
        Context context = new Context();

        context.setVariable("neighborhood", neighborhood);
        context.setVariable("name", firstName);
        context.setVariable("isAdmin", isAdmin);
        context.setVariable("email", email);

        String body = templateEngine.process("welcome", context);

        try {
            Message message = new MimeMessage(sessionEmail);
            message.setFrom(new InternetAddress(smtpUser));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Bienvenido a Resired");
            message.setContent(body, "text/html; charset=utf-8");

            Transport.send(message);
        } catch (MessagingException e) {
            log.error("Error sending email", e);
        }
    }

    @Override
    public void sendAssociateNewUserToNeighborhood(String email, String neighborhood, String firstName) {
        Context context = new Context();

        context.setVariable("neighborhood", neighborhood);
        context.setVariable("name", firstName);
        context.setVariable("email", email);

        String body = templateEngine.process("resident-association", context);
        try {
            Message message = new MimeMessage(sessionEmail);
            message.setFrom(new InternetAddress(smtpUser));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Registro completado a " + neighborhood);
            message.setContent(body, "text/html; charset=utf-8");
            
            Transport.send(message);
        } catch (MessagingException e) {
            log.error("Error sending email", e);
        }
    }
}
