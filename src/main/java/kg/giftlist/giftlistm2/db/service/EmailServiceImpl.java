package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.db.Mail;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl {

    private final JavaMailSender mailSender;

    public void sendEmail(Mail mail,String url){
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("arzimatovanurperi", "puekfbgzcmqcviqn");
            }
        });

        try {

            Message message = new MimeMessage(session);
            //от кого
            message.setFrom(new InternetAddress("arzimatovanurperi@gmail"));
            //кому
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getTo()));
            //Заголовок письма
            message.setSubject(message.getSubject());
            //Содержимое
            message.setText(url);

            //Отправляем сообщение
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
