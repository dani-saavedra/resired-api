package com.resired.api.security.infraestructure.email;

import com.resired.api.security.domain.repository.EmailPort;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JavaSmtpGmailSenderService implements EmailPort {

    private final JavaMailSender emailSender;

    @Override
    public void sendEmailToRecoverPass(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        String url = "http://localhost:3000/account/reset/?token=" + token;
        String body = "Para restablecer su contraseña, haga clic en el siguiente enlace:\n" + url;

        message.setTo(email);
        message.setSubject("Restablecimiento de contraseña");
        message.setText(body);

        emailSender.send(message);
    }
}
