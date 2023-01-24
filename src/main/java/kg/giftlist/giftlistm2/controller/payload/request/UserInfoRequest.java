package kg.giftlist.giftlistm2.controller.payload.request;

import kg.giftlist.giftlistm2.db.entity.ClothingSize;
import kg.giftlist.giftlistm2.db.entity.ShoeSize;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class UserInfoRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String image;
    private String city;
    private LocalDate dateOfBirth;
    private String phoneNumber;
    private List<ClothingSize> clothingSize;
    private String hobby;
    private List<ShoeSize> shoeSize;

}
