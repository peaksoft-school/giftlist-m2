package kg.giftlist.giftlistm2.controller.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminPageUserGetAllResponse {

    private Long id;
    private String first_name;
    private String last_name;
    private String photo;
    private int countGift;
    private Boolean isBlock;

}
