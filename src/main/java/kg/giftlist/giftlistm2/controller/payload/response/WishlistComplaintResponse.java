package kg.giftlist.giftlistm2.controller.payload.response;

import kg.giftlist.giftlistm2.enums.WishListStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class WishlistComplaintResponse {

    private Long id;
    private Long wishListId;
    private String giftName;
    private Long userId;
    private String firstName;
    private String lastName;
    private String link;
    private String holidayName;
    private LocalDate createdAt;
    private String description;
    private String image;
    private WishListStatus wishListStatus;
    private String complainerFirstName;
    private String complainerLastName;
    private String complaintReason;

}
