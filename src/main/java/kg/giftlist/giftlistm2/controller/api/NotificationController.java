package kg.giftlist.giftlistm2.controller.api;

import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "Notification API", description = "User can get all notifications, get all un and is read notifications, get notification by id and delete all notification")
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "Get all notifications ", description = "User can see all notifications")
    @GetMapping
    public List<NotificationResponse> getAllNotification() {
        return notificationService.getAllNotification();
    }

    @Operation(summary = "Get all is read notifications ", description = "User can see all is read notifications")
    @GetMapping("isRead")
    public List<NotificationResponse> getAllIsReadNotification() {
        return notificationService.getAllIsReadNotification();
    }

    @Operation(summary = "Get all un read notifications ", description = "User can see all un read notifications")
    @GetMapping("unRead")
    public List<NotificationResponse> getAllUnReadNotification() {
        return notificationService.getAllUnReadNotification();
    }

    @Operation(summary = "Get notification ", description = "User can get notification by notification id")
    @GetMapping("{id}")
    public NotificationResponse getNotificationById(@PathVariable Long id) {
        return notificationService.getNotificationById(id);
    }

    @Operation(summary = "Delete all notifications ", description = "User can delete all notifications")
    @DeleteMapping
    public String deleteAllNotification() {
        return notificationService.deleteAllNotification();
    }

    @Operation(summary = "Delete notification ", description = "User can delete notification by notification id")
    @DeleteMapping("{id}")
    public String deleteNotificationById(@PathVariable Long id) {
        return notificationService.deleteNotificationById(id);
    }

}
