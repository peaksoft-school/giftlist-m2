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
    private Electronic electronic;
    private Clothes clothes;
    private School school;
    private HouseAndGarden houseAndGarden;
    private Shoe shoe;
    private Transportation transportation;

}
