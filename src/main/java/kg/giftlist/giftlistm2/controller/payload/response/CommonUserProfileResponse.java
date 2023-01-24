package kg.giftlist.giftlistm2.controller.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import kg.giftlist.giftlistm2.db.entity.ClothingSize;
import kg.giftlist.giftlistm2.db.entity.ShoeSize;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class CommonUserProfileResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String image;
    private String city;

    @JsonFormat(pattern = "yyyy.MM.dd")
    private LocalDate dateOfBirth;

    private String phoneNumber;
    private List<ClothingSize> clothingSize;
    private List<ShoeSize> shoeSize;
    private String hobby;
    private String importantNote;
    private List<WishListResponse> wishes;
    private List<HolidayResponse> holidays;
    private List<CharityResponse> charities;
    private Boolean isBlock;

}
