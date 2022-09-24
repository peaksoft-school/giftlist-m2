package kg.giftlist.giftlistm2.controller.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HolidayResponseView {
    private List<HolidayResponse>responses;
}
