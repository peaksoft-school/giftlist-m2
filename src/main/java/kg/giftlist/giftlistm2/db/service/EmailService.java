package kg.giftlist.giftlistm2.db.service;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendEmail(SimpleMailMessage mailMessage);
}
