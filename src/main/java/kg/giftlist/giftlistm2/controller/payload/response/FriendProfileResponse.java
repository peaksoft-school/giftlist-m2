package kg.giftlist.giftlistm2.controller.payload.response;

import kg.giftlist.giftlistm2.db.entity.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class FriendProfileResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String image;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private String city;
    private List<ClothingSize> clothingSize;
    private List<ShoeSize> shoeSize;
    private String hobbies;
    private String inviteStatus;
    private String importantToKnow;
    private List<WishList> wishLists;
    private List<Charity> charities;
    private List<Holiday> holidays;

}
