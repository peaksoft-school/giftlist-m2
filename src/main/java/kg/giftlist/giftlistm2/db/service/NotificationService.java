package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.controller.payload.NotificationResponse;
import kg.giftlist.giftlistm2.db.entity.Charity;
import kg.giftlist.giftlistm2.db.entity.Notification;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.repository.NotificationRepository;
import kg.giftlist.giftlistm2.db.repository.UserRepository;
import kg.giftlist.giftlistm2.enums.NotificationStatus;
import kg.giftlist.giftlistm2.exception.UserNotFoundException;
import kg.giftlist.giftlistm2.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;


    public List<NotificationResponse> getAllNotification(){
        User user = getAuthenticatedUser();
        if (user.getNotifications().isEmpty()){
            log.error("Not found notification");
            throw new UserNotFoundException("Not found notification");
        }
        return view(notificationRepository.getNotificationByUserId(user.getId()));

    }
    public List<NotificationResponse> view(List<Notification> notifications) {
        List<NotificationResponse> responses = new ArrayList<>();
        for (Notification notification : notifications) {
            responses.add(notificationMapper.notificationResponse(notification));
        }
        return responses;
    }

    public Notification sendNotification(User user,Long friendId){

        Notification notification = new Notification();
        notification.setCreated(LocalDate.now());
        notification.setUser(user);
        notification.setReceiverId(friendId);
        notification.setNotificationStatus(NotificationStatus.REQUEST_TO_FRIEND);
        return notification;

    }

    public Notification acceptSendNotification(User user,Long friendId){
        Notification notification = new Notification();
        notification.setCreated(LocalDate.now());
        notification.setUser(user);
        notification.setReceiverId(friendId);
        notification.setNotificationStatus(NotificationStatus.ACCEPT_YOUR_REQUEST);
        return notification;
    }

    public Notification bookedCharity(User user, Long friendId, String giftName){
        Notification notification = new Notification();
        notification.setCreated(LocalDate.now());
        notification.setUser(user);
        notification.setReceiverId(friendId);
        notification.setGiftName(giftName);
        notification.setNotificationStatus(NotificationStatus.BOOKED);
        return notification;
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login);
    }

}
