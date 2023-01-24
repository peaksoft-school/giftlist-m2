package kg.giftlist.giftlistm2.controller.payload.response;

import kg.giftlist.giftlistm2.enums.NotificationStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class NotificationResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private NotificationStatus notificationStatus;
    private Long giftId;
    private String giftName;
    private LocalDate createdAt;
    private boolean isRead;

}
