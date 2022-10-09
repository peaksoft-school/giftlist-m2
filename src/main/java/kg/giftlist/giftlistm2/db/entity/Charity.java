package kg.giftlist.giftlistm2.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kg.giftlist.giftlistm2.enums.Condition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "charity")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Charity {

    @Id
    @GeneratedValue(generator = "charity_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "charity_gen", sequenceName = "charity_seq", allocationSize = 1)
    private Long id;

    private String giftName;

    @Enumerated(EnumType.STRING)
    private Condition condition;

    @Size(max = 10000)
    private String description;

    private String image;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private Category category;

    private LocalDate createdDate;

}
