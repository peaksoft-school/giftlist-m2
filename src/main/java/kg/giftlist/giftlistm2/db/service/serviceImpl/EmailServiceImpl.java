package kg.giftlist.giftlistm2.db.service.serviceImpl;

import kg.giftlist.giftlistm2.db.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    @Async
    public void sendEmail(SimpleMailMessage mailMessage) {
        mailSender.send(mailMessage);
    }
}
