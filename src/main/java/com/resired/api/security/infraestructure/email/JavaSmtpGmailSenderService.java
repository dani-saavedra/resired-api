package com.resired.api.security.infraestructure.email;

import com.resired.api.security.domain.repository.RecoveryPassPort;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JavaSmtpGmailSenderService implements RecoveryPassPort {

    private final JavaMailSender emailSender;

    @Override
    public void sendEmailWithToken(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        String url = "http://localhost:8080/api/account/reset-password?token=" + token;
        String body = "Para restablecer su contraseña, haga clic en el siguiente enlace:\n" + url;

        message.setTo(email);
        message.setSubject("Restablecimiento de contraseña");
        message.setText(body);

        emailSender.send(message);
    }
}
