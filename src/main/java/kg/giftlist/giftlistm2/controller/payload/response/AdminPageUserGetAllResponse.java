package kg.giftlist.giftlistm2.controller.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminPageUserGetAllResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String image;
    private int giftCount;
    private Boolean isBlock;

}
