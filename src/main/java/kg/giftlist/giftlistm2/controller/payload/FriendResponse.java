package kg.giftlist.giftlistm2.controller.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String message;
}
