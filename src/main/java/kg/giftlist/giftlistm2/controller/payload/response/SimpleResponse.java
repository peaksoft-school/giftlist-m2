package kg.giftlist.giftlistm2.controller.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SimpleResponse {

    private String status;
    private String message;

}
