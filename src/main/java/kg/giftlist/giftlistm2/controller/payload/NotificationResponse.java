package kg.giftlist.giftlistm2.controller.payload;

import kg.giftlist.giftlistm2.db.entity.Charity;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.enums.NotificationStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class NotificationResponse {

    private String firstName;
    private String lastName;
    private NotificationStatus notificationStatus;
    private String giftName;
    private LocalDate created;

}
