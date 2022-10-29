package kg.giftlist.giftlistm2.db.repository;

import kg.giftlist.giftlistm2.db.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("select n from User u join u.notifications n where n.receiverId=?1")
    List<Notification> getAllNotificationByUserId(Long id);
    @Query("select n from User u join u.notifications n where n.receiverId=?1")

    Notification getNotificationByUserId(Long id);

}