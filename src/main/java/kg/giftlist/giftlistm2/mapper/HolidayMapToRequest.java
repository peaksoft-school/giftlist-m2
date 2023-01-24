package kg.giftlist.giftlistm2.mapper;

import kg.giftlist.giftlistm2.controller.payload.request.HolidayRequest;
import kg.giftlist.giftlistm2.db.entity.Holiday;
import org.springframework.stereotype.Component;

@Component
public class HolidayMapToRequest {

    public Holiday create(HolidayRequest holidayRequest) {
        if (holidayRequest == null) {
            return null;
        }
        Holiday holiday = new Holiday();
        holiday.setName(holidayRequest.getName());
        holiday.setLocalDate(holidayRequest.getLocalDate());
        holiday.setImage(holidayRequest.getImage());
        return holiday;
    }

    public void update(Holiday holiday, HolidayRequest holidayRequest) {
        holiday.setImage(holidayRequest.getImage());
        holiday.setName(holidayRequest.getName());
        holiday.setLocalDate(holidayRequest.getLocalDate());
    }

}
