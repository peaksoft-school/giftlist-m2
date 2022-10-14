package kg.giftlist.giftlistm2.controller.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class LoginResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String authorities;
    private String jwtToken;
    private String message;

}
