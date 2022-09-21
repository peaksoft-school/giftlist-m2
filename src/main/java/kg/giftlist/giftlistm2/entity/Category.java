package kg.giftlist.giftlistm2.entity;

import kg.giftlist.giftlistm2.enums.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(generator = "category_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "category_gen", sequenceName = "category_seq", allocationSize = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Electronic electronic;

    @Enumerated(EnumType.STRING)
    private Clothes clothes;

    @Enumerated(EnumType.STRING)
    private School school;

    @Enumerated(EnumType.STRING)
    private HouseAndGarden houseAndGarden;

    @Enumerated(EnumType.STRING)
    private Shoe shoe;

    @Enumerated(EnumType.STRING)
    private Transportation transportation;

}
