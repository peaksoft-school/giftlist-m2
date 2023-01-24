package kg.giftlist.giftlistm2.mapper;

import kg.giftlist.giftlistm2.controller.payload.response.HolidayResponse;
import kg.giftlist.giftlistm2.db.entity.Holiday;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HolidayMapToResponse {

    public HolidayResponse viewHoliday(Holiday holiday) {
        if (holiday == null) {
            return null;
        }
        HolidayResponse response = new HolidayResponse();
        response.setId(holiday.getId());
        response.setName(holiday.getName());
        response.setImage(holiday.getImage());
        response.setLocalDate(holiday.getLocalDate());
        return response;
    }

    public List<HolidayResponse> view(List<Holiday> holidays) {
        List<HolidayResponse> responses = new ArrayList<>();
        for (Holiday holiday : holidays) {
            responses.add(viewHoliday(holiday));
        }
        return responses;
    }

}
