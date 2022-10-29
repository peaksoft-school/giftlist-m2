package kg.giftlist.giftlistm2.controller.payload;

import kg.giftlist.giftlistm2.db.entity.Holiday;
import kg.giftlist.giftlistm2.enums.WishListStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class WishListResponse {

    private Long id;
    private String giftName;
    private String link;
    private List<Holiday> holidays;
    private LocalDate holidayDate;
    private String description;
    private String image;
    private WishListStatus wishListStatus;

}
