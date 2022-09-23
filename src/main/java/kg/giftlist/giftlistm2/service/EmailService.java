package kg.giftlist.giftlistm2.service;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    void sendEmail(SimpleMailMessage mailMessage);
}
