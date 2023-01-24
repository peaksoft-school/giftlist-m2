package kg.giftlist.giftlistm2.controller.payload.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

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
