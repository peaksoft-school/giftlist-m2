package kg.giftlist.giftlistm2.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import kg.giftlist.giftlistm2.controller.payload.NotificationResponse;
import kg.giftlist.giftlistm2.db.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/notification")
@CrossOrigin
@RequiredArgsConstructor
@Tag(name = "Notification API", description = "User can")
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping
    public List<NotificationResponse> getAllNotification(){
        return notificationService.getAllNotification();
    }
}
