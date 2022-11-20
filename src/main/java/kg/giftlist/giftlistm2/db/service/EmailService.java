package kg.giftlist.giftlistm2.db.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class EmailService {

    public void sendEmail(Mail mail, String url) {
        Properties props = new Properties();
        props.put("mail. smtp.auth", "true");
        props.put("mail. smtp. starttls.enable", "true");
        props.put("mail. smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return super.getPasswordAuthentication();
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("temuchi500@gmail"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getTo()));
            message.setSubject(message.getSubject());
            message.setText(url);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);

        }
    }
}

