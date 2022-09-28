package kg.giftlist.giftlistm2.controller.payload;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class UserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;

}
