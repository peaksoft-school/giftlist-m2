package kg.giftlist.giftlistm2.mapper;

import com.sun.mail.imap.AppendUID;
import kg.giftlist.giftlistm2.controller.payload.NotificationResponse;
import kg.giftlist.giftlistm2.db.entity.Notification;
import kg.giftlist.giftlistm2.exception.UserNotFoundException;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class NotificationMapper {

    public NotificationResponse notificationResponse(Notification notification){
        if (notification == null){
            throw new UserNotFoundException("Not found notification user email"+notification.getUser().getEmail());
        }
        NotificationResponse notificationResponse = new NotificationResponse();
        notificationResponse.setFirstName(notification.getUser().getFirstName());
        notificationResponse.setLastName(notification.getUser().getLastName());
        notificationResponse.setNotificationStatus(notification.getNotificationStatus());
        notificationResponse.setCreated(LocalDate.now());
        return notificationResponse;
    }


}
