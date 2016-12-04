/**
 * Author: Luis Jesús Pellicer Magallón
 * Year: 2016
 * Version: 1.0
 * Description: This class contains the functionality to
 * send a email.
 */
package tfg.backend.EmailQueue;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

@Component
public class Email {

    @Value("${email.userName}")
    private String userName;

    @Value("${email.pass}")
    private String pass;

    private final Properties props = new Properties() {{
        put("mail.smtp.host", "smtp.gmail.com");
        put("mail.smtp.auth", "true");
        put("mail.smtp.port", "587");
        put("mail.smtp.ssl.trust", "smtp.gmail.com");
        put("mail.smtp.starttls.enable", "true");

    }};

    public Email() {

    }

    public void sendEmail(String title, String body, String destination) {

        Session session = Session.getInstance(props, new Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(userName, pass);
            }
        });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(userName));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse
                    (destination));
            message.setSubject(title);
            message.setText(body);
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}