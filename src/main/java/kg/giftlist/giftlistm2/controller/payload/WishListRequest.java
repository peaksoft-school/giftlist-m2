package kg.giftlist.giftlistm2.controller.payload;

import kg.giftlist.giftlistm2.db.entity.Holiday;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class WishListRequest {

    private String giftName;
    private String link;
    private Long holidayId;
    private LocalDate holidayDate;
    private String description;
    private String image;

}
