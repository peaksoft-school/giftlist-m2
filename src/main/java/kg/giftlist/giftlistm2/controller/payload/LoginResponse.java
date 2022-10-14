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

//    @Override
//    public String toString() {
//        return "id: " + id + "\n" +
//                "firstName: " + firstName + "\n" +
//                "lastName: " + lastName + "\n" +
//                "email: " + email + "\n" +
//                "authorities: " + authorities + "\n" +
//                "jwtToken: " + jwtToken + "\n" +
//                "message: " + message;
//    }

}
