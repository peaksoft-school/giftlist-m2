package kg.giftlist.giftlistm2.controller.payload;

import kg.giftlist.giftlistm2.enums.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryResponse {

    private Electronic electronic;
    private Clothes clothes;
    private School school;
    private HouseAndGarden houseAndGarden;
    private Shoe shoe;
    private Transportation transportation;

}
