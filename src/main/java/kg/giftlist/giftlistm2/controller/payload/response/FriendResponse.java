package kg.giftlist.giftlistm2.controller.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private int wishListCount;
    private int holidayCount;
    private String message;

}
