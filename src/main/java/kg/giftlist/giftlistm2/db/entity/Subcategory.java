package kg.giftlist.giftlistm2.db.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "subcategories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Subcategory {

    @Id
    @GeneratedValue(generator = "subcategory_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "subcategory_gen", sequenceName = "subcategory_seq", allocationSize = 1)
    private Long id;

    private String subcategoryName;

    @ManyToOne
    private Category category;

}
