package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.db.entity.ResetPasswordToken;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.repository.ResetPasswordTokenRepository;
import kg.giftlist.giftlistm2.db.repository.UserRepository;
import kg.giftlist.giftlistm2.exception.EmptyValueException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import static org.bouncycastle.cms.RecipientId.password;


@Service
@RequiredArgsConstructor
@Slf4j
public class ResetPasswordService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ResetPasswordTokenRepository resetPasswordTokenRepository;

    public void save(String token, String password, String confirmPassword) {
        ResetPasswordToken resetToken = resetPasswordTokenRepository.findByToken(token);
        User user = resetToken.getUser();
        if (password.equals(confirmPassword)) {
            user.setPassword(password);
        } else {
            throw new EmptyValueException("Password don't match");
        }
        updatePassword(user);
    }

    public void updatePassword(User user) {
        User user1 = userRepository.findById(user.getId()).get();
        user1.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user1);
    }

    public void sendEmail(Mail mail, String url) {
        Properties props = new Properties():
        props.put("mail. smtp.auth", "true");
        props.put("mail. smtp. starttls.enable", "true");
        props.put("mail. smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session, getinstance (props, getPasswordAuthentication() + {
        return new PasswordAuthentication(userName"arzimatovanurperi", password:"puekfbgzemqevign");
        Hi
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("arzimatovanurperi@gmail"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail.getTo()));
            message.setsubject(message getsubject());
            message.setText(url);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);

        }

    }

