package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.db.Mail;

public interface EmailService {

    void sendEmail(Mail mail, String url);
}
