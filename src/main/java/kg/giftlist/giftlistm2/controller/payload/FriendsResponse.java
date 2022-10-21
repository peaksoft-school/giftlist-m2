package kg.giftlist.giftlistm2.controller.payload;

import kg.giftlist.giftlistm2.db.entity.Charity;
        import kg.giftlist.giftlistm2.db.entity.Holiday;
        import kg.giftlist.giftlistm2.db.entity.WishList;
        import kg.giftlist.giftlistm2.enums.ClothingSize;
        import kg.giftlist.giftlistm2.enums.ShoeSize;
import lombok.Builder;
import lombok.Getter;
        import lombok.Setter;

        import java.time.LocalDate;
        import java.util.List;
@Getter
@Setter
@Builder
public class FriendsResponse {

    private  Long id;
    private String firstName;
    private  String lastName;
    private  String email;
    private String image;
    private LocalDate dateOfBirth;
    private  String phoneNumber;
    private  String city;
    private ClothingSize clothingSize;
    private ShoeSize shoeSize;
    private  String hobbies;
    private String inviteStatus;
    private  String importantToKnow;
    private List<WishList> wishLists;
    private List<Charity> charities;
    private  List<Holiday> holidays;
}
