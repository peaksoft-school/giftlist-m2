package kg.giftlist.giftlistm2.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.giftlist.giftlistm2.controller.payload.response.NotificationResponse;
import kg.giftlist.giftlistm2.db.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/notifications")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Notification API", description = "User can get all notifications, get all un and is read notifications, get notification by id and delete all notification")
public class NotificationApi {

    private final NotificationService notificationService;

    @Operation(summary = "Get all notifications ", description = "User can see all notifications")
    @GetMapping
    public List<NotificationResponse> getAllNotification() {
        return notificationService.getAllNotification();
    }

    @Operation(summary = "Get all is read notifications ", description = "User can see all is read notifications")
    @GetMapping("is-read")
    public List<NotificationResponse> getAllIsReadNotification() {
        return notificationService.getAllIsReadNotification();
    }

    @Operation(summary = "Get all un read notifications ", description = "User can see all un read notifications")
    @GetMapping("un-read")
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

}
