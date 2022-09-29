package kg.giftlist.giftlistm2.controller.payload;

import kg.giftlist.giftlistm2.db.entity.Category;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.enums.Condition;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class CharityResponse {

    private Long id;
    private String giftName;
    private User user;
    private Condition condition;
    private Category category;
    private String image;
    private String description;
    private LocalDate createdDate;

}
