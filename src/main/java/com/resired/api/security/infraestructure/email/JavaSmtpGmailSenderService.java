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

    @Override
    public void sendRegisteredResidentEmail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        String body = "Bienvenido a Resired, tu cuenta a sido registrada satisfactoriamente y ya podrás ingresas " +
            "a la app y hacer uso de ella, si aún no la tienes, descargala desde todas las tiendas." +
            "Ten presente que tu usuario será tu correo electronico y tu clave de acceso por primera vez tu documento";

        message.setTo(email);
        message.setSubject("Bienvenido a Resired");
        message.setText(body);

        emailSender.send(message);
    }

    @Override
    public void sendAssociateNewResidentToResidentEmail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        String body = "Tu cuenta en resired ha sido vinculada a una nueva residencia, podrás ingresar con tus mismas credenciales";

        message.setTo(email);
        message.setSubject("Registrado a nueva residencia");
        message.setText(body);

        emailSender.send(message);
    }
}
