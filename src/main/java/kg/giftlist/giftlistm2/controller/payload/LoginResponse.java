package kg.giftlist.giftlistm2.controller.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class LoginResponse {

    private String jwtToken;
    private String message;
    private String authorities;
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

}
