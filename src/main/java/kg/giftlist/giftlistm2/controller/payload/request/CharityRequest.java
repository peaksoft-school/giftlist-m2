package kg.giftlist.giftlistm2.controller.payload.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CharityRequest {

    private String giftName;
    private String condition;
    private String image;
    private String description;
    private Long categoryId;
    private Long subcategoryId;

}
