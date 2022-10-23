package kg.giftlist.giftlistm2.controller.payload;

import kg.giftlist.giftlistm2.enums.InviteStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
public class Response {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String message;
}
