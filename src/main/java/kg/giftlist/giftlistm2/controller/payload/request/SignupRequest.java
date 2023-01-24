package kg.giftlist.giftlistm2.controller.payload.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;
    private boolean isSubscribeToNewsletter = false;

}
