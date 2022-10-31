package kg.giftlist.giftlistm2.controller.payload;

import kg.giftlist.giftlistm2.db.entity.WishList;
import kg.giftlist.giftlistm2.enums.Condition;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookingResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String gitName;
    private String charityImage;
    private Condition condition;
    private LocalDate created;
    private WishList wishList;

}
