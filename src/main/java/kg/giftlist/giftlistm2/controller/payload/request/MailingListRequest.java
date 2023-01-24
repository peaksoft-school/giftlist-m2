package kg.giftlist.giftlistm2.controller.payload.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MailingListRequest {

    private String image;
    private String header;
    private String text;

}
