package kg.giftlist.giftlistm2.controller.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class HolidayResponse {

    private Long id;
    private String name;

    @JsonFormat(pattern = "yyyy.MM.dd")
    private LocalDate localDate;

    private String image;

}
