package kg.giftlist.giftlistm2.db.repository;

import kg.giftlist.giftlistm2.db.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {


    @Query("select n FROM Notification n join n.receivers r where r.id=?1")
    List<Notification> getAllNotificationByUserId(Long id);

    @Query("select n From Notification n join n.receivers r where n.receivers=?1")
    Notification getNotificationByUserId(Long id);

    @Query("select n From Notification n join n.receivers r where n.read=true and r.id=?1")
    List<Notification> getAllIsReadNotification(Long id);

    @Query("select n From Notification n join n.receivers r where n.read=false and r.id=?1")
    List<Notification> getAllUnReadNotification(Long id);



}