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
    public void sendRegisteredUserEmail(String email, String neighborhood) {
        SimpleMailMessage message = new SimpleMailMessage();
        String body = "Bienvenido a ResiRed, tu cuenta a sido asociada satisfactoriamente a " + neighborhood + " y ya podrás ingresas " +
            "a la app ResiRed y hacer uso de ella. Si aún no tienes la app, descargala desde todas las tiendas." +
            "Ten presente que tu usuario será tu correo electronico diligenciado en tu solicitud y la clave de acceso por primera " +
            "vez será tu documento";

        message.setTo(email);
        message.setSubject("Bienvenido a Resired");
        message.setText(body);

        emailSender.send(message);
    }

    @Override
    public void sendAssociateNewUserToNeighborhood(String email, String neighborhood) {
        SimpleMailMessage message = new SimpleMailMessage();
        String body = "Tu cuenta en resired ha sido vinculada a " + neighborhood + ", ten presente que tu ingreso será con las mismas crendenciales.";

        message.setTo(email);
        message.setSubject("Registro completado a " + neighborhood);
        message.setText(body);

        emailSender.send(message);
    }
}
