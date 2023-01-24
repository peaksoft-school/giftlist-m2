package kg.giftlist.giftlistm2.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subcategories")
public class Subcategory {

    @Id
    @GeneratedValue(generator = "subcategory_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "subcategory_gen", sequenceName = "subcategory_seq", allocationSize = 1)
    private Long id;

    private String subcategoryName;

    @ManyToOne
    @JsonIgnore
    private Category category;

    @OneToMany
    @JsonIgnore
    private List<Charity> charities;

}
