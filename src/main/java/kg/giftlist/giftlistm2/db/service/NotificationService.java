package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.db.entity.Notification;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.enums.NotificationStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    public Notification sendNotification(User user,Long friendId){
        Notification notification = new Notification();
        notification.setCreated(LocalDate.now());
        notification.setUser(user);
        notification.setReceiverId(friendId);
        return notification;
    }

}
