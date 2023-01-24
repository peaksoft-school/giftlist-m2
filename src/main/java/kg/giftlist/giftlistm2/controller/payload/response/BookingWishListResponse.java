package kg.giftlist.giftlistm2.controller.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookingWishListResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String WishListGiftName;
    private String HolidayName;
    private String WishListImage;
    private LocalDate createdAt;

}
