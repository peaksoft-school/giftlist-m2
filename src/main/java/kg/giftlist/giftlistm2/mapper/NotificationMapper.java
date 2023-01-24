package kg.giftlist.giftlistm2.mapper;

import kg.giftlist.giftlistm2.controller.payload.response.NotificationResponse;
import kg.giftlist.giftlistm2.db.entity.Notification;
import kg.giftlist.giftlistm2.exception.UserNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class NotificationMapper {

    public NotificationResponse notificationResponse(Notification notification) {
        if (notification == null) {
            throw new UserNotFoundException("Not found notification user email");
        }
        NotificationResponse notificationResponse = new NotificationResponse();
        notificationResponse.setId(notification.getId());
        notificationResponse.setFirstName(notification.getUser().getFirstName());
        notificationResponse.setLastName(notification.getUser().getLastName());
        notificationResponse.setGiftId(notification.getGiftId());
        notificationResponse.setGiftName(notification.getGiftName());
        notificationResponse.setNotificationStatus(notification.getNotificationStatus());
        notificationResponse.setCreatedAt(LocalDate.now());
        notificationResponse.setRead(notification.isRead());
        return notificationResponse;
    }

}
