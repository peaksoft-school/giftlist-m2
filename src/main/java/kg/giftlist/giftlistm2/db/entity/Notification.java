package kg.giftlist.giftlistm2.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kg.giftlist.giftlistm2.enums.NotificationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "notification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(generator = "notification_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "notification_gen", sequenceName = "notification_seq", allocationSize = 1)
    private Long id;

    private Long receiverId;

    private LocalDate created;

    @Enumerated(EnumType.STRING)
    private NotificationStatus notificationStatus;

    private String giftName;

    private boolean read;
    @ManyToOne
    private User user;
    @ManyToOne
    @JsonIgnore
    private Charity charity;
//    public void isRead(boolean read){
//        this.read=read;
//    }
    }


