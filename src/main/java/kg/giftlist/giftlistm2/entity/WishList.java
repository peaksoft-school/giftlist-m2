package kg.giftlist.giftlistm2.entity;

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

    private String giftName;

    private String link;

    private LocalDate holidayDate;

    @Size(max = 10000)
    private String description;

    private String image;

    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinTable(name = "wish_list_holiday",
            joinColumns = @JoinColumn(name = "wish_list_id"),
            inverseJoinColumns = @JoinColumn(name = "holiday_id"))
    private List<Holiday> holidays;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
