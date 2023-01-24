package kg.giftlist.giftlistm2.db.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "holiday")
public class Holiday {

    @Id
    @GeneratedValue(generator = "holiday_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "holiday_gen", sequenceName = "holiday_seq", allocationSize = 1)
    private Long id;

    private String name;

    @JsonFormat(pattern = "yyyy.MM.dd")
    private LocalDate localDate;

    private String image;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JsonIgnore
    private List<WishList> wishLists;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

}
