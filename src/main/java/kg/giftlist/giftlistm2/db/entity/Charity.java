package kg.giftlist.giftlistm2.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kg.giftlist.giftlistm2.enums.CharityStatus;
import kg.giftlist.giftlistm2.enums.Condition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "charity")
public class Charity {

    @Id
    @GeneratedValue(generator = "charity_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "charity_gen", sequenceName = "charity_seq", allocationSize = 1)
    private Long id;

    private String giftName;

    private boolean isBlocked;

    @Enumerated(EnumType.STRING)
    private Condition condition;

    @Size(max = 10000)
    private String description;

    private String image;

    private Boolean isBlock;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "category_id")
    private Category category;

    @CreatedDate
    private LocalDate createdAt;

    @OneToOne(mappedBy = "charity", cascade = CascadeType.ALL)
    private Booking booking;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "subcategory_id")
    private Subcategory subcategory;

    @Enumerated(EnumType.STRING)
    private CharityStatus charityStatus;

    @OneToMany(mappedBy = "charity", cascade = CascadeType.ALL)
    private List<Notification> notifications = new ArrayList<>();

    @OneToMany(mappedBy = "charity", cascade = CascadeType.ALL)
    private List<Complaints> complaints;

    public void addNotification(Notification notification) {
        notifications.add(notification);
    }

}
