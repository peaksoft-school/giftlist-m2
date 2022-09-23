package kg.giftlist.giftlistm2.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {

    private String email;
    private String password;

}
