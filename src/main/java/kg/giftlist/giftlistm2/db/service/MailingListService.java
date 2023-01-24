package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.controller.payload.request.MailingListRequest;
import kg.giftlist.giftlistm2.controller.payload.response.MailingListResponse;
import kg.giftlist.giftlistm2.db.entity.MailingList;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.repository.MailingListRepository;
import kg.giftlist.giftlistm2.db.repository.UserRepository;
import kg.giftlist.giftlistm2.exception.EmptyValueException;
import kg.giftlist.giftlistm2.mapper.MailingListMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailingListService {

    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;
    private final MailingListRepository mailingListRepository;
    private final MailingListMapper mailingListMapper;

    public MailingListResponse send(MailingListRequest request) {
        List<User> userList = userRepository.findAll();
        for (User user : userList) {
            if (user.isSubscribeToNewsletter()) {
                SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                simpleMailMessage.setFrom("temuchi500@gmail.com");
                simpleMailMessage.setTo(user.getEmail());
                simpleMailMessage.setSubject(request.getHeader());
                simpleMailMessage.setText(request.getText());
                simpleMailMessage.setText(request.getText());
                this.javaMailSender.send(simpleMailMessage);
            }
        }
        log.info("Mailing list successful send");
        return mailingListMapper.mapToResponse(mailingListRepository.save(mailingListMapper.mapToEntity(request)));
    }

    public List<MailingListResponse> getAllMailingList() {
        List<MailingList> mailingList = mailingListRepository.findAll();
        log.info("Get all Mailing list");
        return view(mailingList);
    }

    public MailingListResponse getMailingListById(Long id) {
        MailingList mailingList = mailingListRepository.findById(id).orElseThrow(() ->
                new EmptyValueException("Mailing list is empty"));
        log.info("Get mailing with id: " + id);
        return mailingListMapper.mapToResponse(mailingList);
    }

    public List<MailingListResponse> view(List<MailingList> mailingLists) {
        List<MailingListResponse> responses = new ArrayList<>();
        for (MailingList us : mailingLists) {
            responses.add(mailingListMapper.mapToResponse(us));
        }
        return responses;
    }

}
