package kg.giftlist.giftlistm2.db.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "holiday")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Holiday {
    @Id
    @GeneratedValue(generator = "holiday_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "holiday_gen", sequenceName = "holiday_seq", allocationSize = 1)
    private Long id;

    private String name;
     
    @JsonFormat(pattern = "yyyy.MM.dd")
    private LocalDate localDate;

    private String image;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "wish_list_holiday",
            joinColumns = @JoinColumn(name = "holiday_id"),
            inverseJoinColumns = @JoinColumn(name = "wish_list_id"))
    private List<WishList> wishLists;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
