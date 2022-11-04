package kg.giftlist.giftlistm2.controller.payload;

import kg.giftlist.giftlistm2.db.entity.Complaints;
import kg.giftlist.giftlistm2.enums.WishListStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class ComplainedWishListResponse {

    private Long id;
    private String giftName;
    private Long userId;
    private String firstName;
    private String lastName;
    private String link;
    private String holidayName;
    private LocalDate holidayDate;
    private String description;
    private String image;
    private WishListStatus wishListStatus;
    private String complainingUserName;
    private String complainingUserLastname;
    private String complaintCause;

}
