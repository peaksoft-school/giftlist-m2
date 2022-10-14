package kg.giftlist.giftlistm2.controller.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {

    private String jwtToken;
    private String message;
    private String authorities;
    private Long id;
    private String email;

}


