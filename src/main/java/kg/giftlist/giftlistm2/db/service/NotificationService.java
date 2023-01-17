package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.controller.payload.response.NotificationResponse;
import kg.giftlist.giftlistm2.db.entity.*;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    public List<NotificationResponse> getAllNotification() {
        User user = getAuthenticatedUser();
        List<Notification> notifications = notificationRepository.getAllNotificationByUserId(user.getId());
        notifications.forEach(x -> x.setRead(true));
        notificationRepository.saveAll(notifications);
        log.info("Get all notifications");
        return view(notifications);
    }

    public List<NotificationResponse> getAllIsReadNotification() {
        User user = getAuthenticatedUser();
        List<Notification> notifications = notificationRepository.getAllIsReadNotification(user.getId());
        log.info("Get all is read notifications");
        return view(notifications);
    }

    public List<NotificationResponse> getAllUnReadNotification() {
        User user = getAuthenticatedUser();
        List<Notification> notifications = notificationRepository.getAllUnReadNotification(user.getId());
        log.info("Get all un read notifications");
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
        log.info("Get notification with id: " + id);
        return notificationMapper.notificationResponse(notification);
    }

    public List<NotificationResponse> view(List<Notification> notifications) {
        List<NotificationResponse> responses = new ArrayList<>();
        for (Notification notification : notifications) {
            responses.add(notificationMapper.notificationResponse(notification));
        }
        return responses;
    }

    public Notification sendNotification(User user, List<User> receivers) {
        Notification notification = new Notification();
        notification.setCreatedAt(LocalDate.now());
        notification.setUser(user);
        notification.setReceivers(receivers);
        notification.setNotificationStatus(NotificationStatus.REQUEST_TO_FRIEND);
        log.info("Notification status: " + NotificationStatus.REQUEST_TO_FRIEND);
        log.info("Successfully send notification");
        return notification;
    }

    public Notification acceptSendNotification(User user, List<User> receivers) {
        Notification notification = new Notification();
        notification.setCreatedAt(LocalDate.now());
        notification.setUser(user);
        notification.setReceivers(receivers);
        notification.setNotificationStatus(NotificationStatus.ACCEPT_YOUR_REQUEST);
        log.info("Notification status: " + NotificationStatus.ACCEPT_YOUR_REQUEST);
        log.info("Successfully send notification");
        return notification;
    }

    public Notification bookedCharity(User user, List<User> receivers, Charity charity) {
        Notification notification = new Notification();
        notification.setCreatedAt(LocalDate.now());
        notification.setUser(user);
        notification.setReceivers(receivers);
        notification.setGiftId(charity.getId());
        notification.setGiftName(charity.getGiftName());
        notification.setNotificationStatus(NotificationStatus.BOOKED);
        log.info("Notification status: " + NotificationStatus.BOOKED);
        log.info("Successfully send notification");
        return notification;
    }

    public Notification sendCharityComplaintNotification(User user, List<User> receivers, Complaints complaints) {
        Notification notification = new Notification();
        notification.setCreatedAt(LocalDate.now());
        notification.setUser(user);
        notification.setReceivers(receivers);
        notification.setGiftId(complaints.getCharity().getId());
        notification.setGiftName(complaints.getCharity().getGiftName());
        notification.setNotificationStatus(NotificationStatus.HAS_COMPLAINED);
        log.info("Notification status: " + NotificationStatus.HAS_COMPLAINED);
        log.info("Successfully send notification");
        return notification;
    }

    public Notification sendWishlistComplaintNotification(User user, List<User> receivers, Complaints complaints) {
        Notification notification = new Notification();
        notification.setCreatedAt(LocalDate.now());
        notification.setUser(user);
        notification.setReceivers(receivers);
        notification.setGiftId(complaints.getWishList().getId());
        notification.setGiftName(complaints.getWishList().getGiftName());
        notification.setNotificationStatus(NotificationStatus.HAS_COMPLAINED);
        log.info("Notification status: " + NotificationStatus.HAS_COMPLAINED);
        log.info("Successfully send notification");
        return notification;
    }

    public Notification wishListNotification(User user, List<User> receivers, WishList wishList) {
        Notification notification = new Notification();
        notification.setCreatedAt(LocalDate.now());
        notification.setUser(user);
        notification.setReceivers(receivers);
        notification.setGiftId(wishList.getId());
        notification.setGiftName(wishList.getGiftName());
        notification.setNotificationStatus(NotificationStatus.ADDED_WISHED_GIFT);
        log.info("Notification status: " + NotificationStatus.ADDED_WISHED_GIFT);
        log.info("Successfully send notification");
        return notification;
    }

    public String deleteAllNotification() {
        User user = getAuthenticatedUser();
        List<Notification> notifications = notificationRepository.getAllNotificationByUserId(user.getId());
        if (notifications.isEmpty()) {
            log.error("Not found notification");
            throw new NotificationNotFoundException("Not found notification");
        }
        for (Notification notification : notifications) {
            notification.deleteUser(user);
        }
        notificationRepository.saveAll(notifications);
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
