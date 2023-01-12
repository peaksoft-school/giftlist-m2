package kg.giftlist.giftlistm2.controller.payload.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class MailingListResponse {

    private Long id;
    private String image;
    private String header;
    private String text;
    private LocalDate createdAt;

}

