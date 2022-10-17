package kg.giftlist.giftlistm2.controller.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NotBlank(message = "The field must not be empty")
public class SignupRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;

}
