package kg.giftlist.giftlistm2.controller.payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String authorities;
    private String jwtToken;
    private String message;

}
