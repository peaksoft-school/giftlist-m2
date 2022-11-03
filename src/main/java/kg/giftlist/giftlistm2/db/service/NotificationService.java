package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.controller.payload.NotificationResponse;
import kg.giftlist.giftlistm2.db.entity.Charity;
import kg.giftlist.giftlistm2.db.entity.Notification;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.repository.NotificationRepository;
import kg.giftlist.giftlistm2.db.repository.UserRepository;
import kg.giftlist.giftlistm2.enums.NotificationStatus;
import kg.giftlist.giftlistm2.exception.NotificationNotFoundException;
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

    public List<NotificationResponse> getAllNotification() {
        User user = getAuthenticatedUser();
        List<Notification> notifications = notificationRepository.getAllNotificationByUserId(user.getId());
        if (notifications.isEmpty()) {
            log.error("Not found notification");
            throw new NotificationNotFoundException("Not found notification");
        }
        notifications.stream().forEach(x -> x.setRead(true));
        notificationRepository.saveAll(notifications);
        return view(notifications);
    }

    public List<NotificationResponse> getAllIsReadNotification() {
        User user = getAuthenticatedUser();
        List<Notification> notifications = notificationRepository.getAllIsReadNotification(user.getId());
        if (notifications.isEmpty()) {
            throw new NotificationNotFoundException("Read notification not found");
        }
        return view(notifications);
    }

    public List<NotificationResponse> getAllUnReadNotification() {
        User user = getAuthenticatedUser();
        List<Notification> notifications = notificationRepository.getAllUnReadNotification(user.getId());
        if (notifications.isEmpty()) {
            throw new NotificationNotFoundException("Read notification not found");
        }
        return view(notifications);
    }

    public NotificationResponse getNotificationById(Long id) {
        User user = getAuthenticatedUser();
        Notification notification = notificationRepository.getNotificationByUserId(user.getId());
        if (notification == null) {
            throw new NotificationNotFoundException("Not found Notification with id: " + id);
        }
        notification.setRead(true);
        notificationRepository.save(notification);
        return notificationMapper.notificationResponse(notification);
    }

    public List<NotificationResponse> view(List<Notification> notifications) {
        List<NotificationResponse> responses = new ArrayList<>();
        for (Notification notification : notifications) {
            responses.add(notificationMapper.notificationResponse(notification));
        }
        return responses;
    }

    public Notification sendNotification(User user,List<User>receivers) {
        Notification notification = new Notification();
        notification.setCreated(LocalDate.now());
        notification.setUser(user);
        notification.setReceivers(receivers);
        notification.setNotificationStatus(NotificationStatus.REQUEST_TO_FRIEND);
        log.info("Notification status: " + NotificationStatus.REQUEST_TO_FRIEND);
        return notification;
    }

    public Notification acceptSendNotification(User user, List<User>receivers) {
        Notification notification = new Notification();
        notification.setCreated(LocalDate.now());
        notification.setUser(user);
        notification.setReceivers(receivers);
        notification.setNotificationStatus(NotificationStatus.ACCEPT_YOUR_REQUEST);
        log.info("Notification status: " + NotificationStatus.ACCEPT_YOUR_REQUEST);
        return notification;
    }

    public Notification bookedCharity(User user,List<User>receivers, Charity charity) {
        Notification notification = new Notification();
        notification.setCreated(LocalDate.now());
        notification.setUser(user);
        notification.setReceivers(receivers);
        notification.setGiftName(charity.getGiftName());
        notification.setNotificationStatus(NotificationStatus.BOOKED);
        return notification;
    }

    public String deleteAllNotification() {
        User user = getAuthenticatedUser();
        List<Notification> notifications = notificationRepository.getAllNotificationByUserId(user.getId());
        if (notifications.isEmpty()) {
            log.error("Not found notification");
            throw new NotificationNotFoundException("Not found notification");
        }
        notificationRepository.deleteAll(notifications);
        log.info("Successfully deleted all notification");
        return "Successfully deleted all notifications";
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        log.info("User: " + authentication.getName());
        return userRepository.findByEmail(login);
    }

}
