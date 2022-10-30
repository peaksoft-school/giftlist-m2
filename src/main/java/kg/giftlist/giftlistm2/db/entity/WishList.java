package kg.giftlist.giftlistm2.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kg.giftlist.giftlistm2.enums.WishListStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "wish_list")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WishList {

    @Id
    @GeneratedValue(generator = "wish_list_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "wish_list_gen", sequenceName = "wish_list_seq", allocationSize = 1)
    private Long id;

    @Column(name = "gift_name")
    private String giftName;

    private String link;

    @Column(name = "holyday_date")
    private LocalDate holidayDate;

    @Size(max = 10000)
    private String description;

    private String image;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "holiday_id")
    @JsonIgnore
    private Holiday holidays;

    @Enumerated(EnumType.STRING)
    private WishListStatus wishListStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

}
