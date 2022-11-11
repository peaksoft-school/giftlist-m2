package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.controller.payload.MailingListRequest;
import kg.giftlist.giftlistm2.controller.payload.MailingListResponse;
import kg.giftlist.giftlistm2.db.entity.MailingList;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.repository.MailingListRepository;
import kg.giftlist.giftlistm2.db.repository.UserRepository;
import kg.giftlist.giftlistm2.mapper.MailingListMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailingListService {
    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;
    private final MailingListRepository mailingListRepository;
    private final MailingListMapper mailingListMapper;

    public MailingListResponse send(MailingListRequest request) {
        List<User> userList = userRepository.findAll();

        for (User user : userList) {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("admin@gmail.com");
            simpleMailMessage.setTo(user.getEmail());
            simpleMailMessage.setSubject(request.getHeader());
            simpleMailMessage.setText(request.getText());
            this.javaMailSender.send(simpleMailMessage);
        }
        return mailingListMapper.mapToResponse(mailingListRepository.save(mailingListMapper.mapToEntity(request)));
    }

}
