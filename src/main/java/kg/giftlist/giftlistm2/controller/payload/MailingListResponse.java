package kg.giftlist.giftlistm2.controller.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class MailingListResponse {

    private String image;
    private String header;
    private String text;
    private LocalDate createdAt;

}

