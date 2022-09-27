package kg.giftlist.giftlistm2.controller.payload;

import kg.giftlist.giftlistm2.db.entity.Category;
import kg.giftlist.giftlistm2.enums.Condition;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CharityRequest {

    private String giftName;

    private Condition condition;

    private Category category;

    private String image;

    private String description;

}
