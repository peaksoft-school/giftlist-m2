package kg.giftlist.giftlistm2.db.repository;

import kg.giftlist.giftlistm2.db.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("SELECT n FROM Notification n JOIN n.receivers r WHERE r.id=?1")
    List<Notification> getAllNotificationByUserId(Long id);

    @Query("SELECT n FROM Notification n JOIN n.receivers r WHERE n.receivers=?1")
    Notification getNotificationByUserId(Long id);

    @Query("SELECT n FROM Notification n JOIN n.receivers r WHERE n.isRead=true AND r.id=?1")
    List<Notification> getAllIsReadNotification(Long id);

    @Query("SELECT n FROM Notification n JOIN n.receivers r WHERE n.isRead=false AND r.id=?1")
    List<Notification> getAllUnReadNotification(Long id);

}