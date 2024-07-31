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
        String url = "https://admin.resired.site/account/reset?token=" + token;
        String body = "Para restablecer su contraseña, haga clic en el siguiente enlace:\n" + url;

        message.setTo(email);
        message.setSubject("Restablecimiento de contraseña");
        message.setText(body);

        emailSender.send(message);
    }

    @Override
    public void sendRegisteredUserEmail(String email, String neighborhood) {
        SimpleMailMessage message = new SimpleMailMessage();
        String body = "¡Bienvenido a ResiRed! \n \n" +
            "Tu cuenta ha sido asociada con éxito a " + neighborhood + ". Ahora puedes acceder a nuestra app para empezar a disfrutar de todas las ventajas que tenemos para ofrecerte. Si aún no tienes la aplicación, puedes descargarla desde cualquier tienda.\n" +
            "\nRecuerda que tu usuario es el correo electrónico que diligenciaste en tu solicitud y para acceder por primera vez deberás usar tu número de documento como contraseña.\n" +
            "\n¡Gracias por ser parte de nuestra comunidad! Siempre estamos dispuestos a ayudarte en cualquier cosa que necesites.";

        message.setTo(email);
        message.setSubject("Bienvenido a Resired");
        message.setText(body);

        emailSender.send(message);
    }

    @Override
    public void sendAssociateNewUserToNeighborhood(String email, String neighborhood) {
        SimpleMailMessage message = new SimpleMailMessage();
        String body = "Tu cuenta en resired ha sido vinculada ha " + neighborhood + ", ten presente que tu ingreso será con las mismas crendenciales.";

        message.setTo(email);
        message.setSubject("Registro completado a " + neighborhood);
        message.setText(body);

        emailSender.send(message);
    }
}
