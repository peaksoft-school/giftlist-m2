package kg.giftlist.giftlistm2.controller.payload;

import kg.giftlist.giftlistm2.db.entity.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CategoryResponse {

    private String electronic;
    private String clothes;
    private String school;
    private String houseAndGarden;
    private String shoe;
    private String transport;

}
