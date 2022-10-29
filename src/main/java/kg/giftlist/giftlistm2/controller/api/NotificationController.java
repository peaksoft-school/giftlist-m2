package kg.giftlist.giftlistm2.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import kg.giftlist.giftlistm2.controller.payload.NotificationResponse;
import kg.giftlist.giftlistm2.db.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/notification")
@CrossOrigin
@RequiredArgsConstructor
@Tag(name = "Notification API", description = "User can")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public List<NotificationResponse> getAllNotification() {
        return notificationService.getAllNotification();
    }

    @GetMapping("isRead")
    public List<NotificationResponse> getAllIsReadNotification() {
        return notificationService.getAllIsReadNotification();
    }

    @GetMapping("unRead")
    public List<NotificationResponse> getAllUnReadNotification() {
        return notificationService.getAllUnReadNotification();
    }

    @GetMapping("{id}")
    public NotificationResponse getNotificationById(@PathVariable Long id) {
        return notificationService.getNotificationById(id);
    }

    @DeleteMapping
    public String deleteAllNotification() {
        return notificationService.deleteAllNotification();
    }

    @DeleteMapping("{id}")
    public String deleteNotificationById(@PathVariable Long id) {
        return notificationService.deleteNotificationById(id);
    }

}
