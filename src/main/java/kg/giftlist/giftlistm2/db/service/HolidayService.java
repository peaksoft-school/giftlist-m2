package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.controller.payload.request.HolidayRequest;
import kg.giftlist.giftlistm2.controller.payload.response.HolidayResponse;
import kg.giftlist.giftlistm2.db.entity.Holiday;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.repository.HolidayRepository;
import kg.giftlist.giftlistm2.db.repository.UserRepository;
import kg.giftlist.giftlistm2.mapper.HolidayMapToRequest;
import kg.giftlist.giftlistm2.mapper.HolidayMapToResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class HolidayService {

    private final HolidayRepository repository;
    private final UserRepository userRepository;
    private final HolidayMapToRequest holidayMapToRequest;
    private final HolidayMapToResponse holidayMapToResponse;

    public HolidayResponse create(HolidayRequest holidayRequest) {
        Holiday holiday = repository.save(holidayMapToRequest.create(holidayRequest));
        if (holidayRequest.getImage() == null) {
            holiday.setImage("https://funart.pro/uploads/posts/2021-04/1618558235_23-funart_pro-p-oboi-fon-fon-podarki-37.jpg");
        } else {
            holiday.setImage(holidayRequest.getImage());
        }
        User user = getAuthenticatedUser();
        holiday.setUser(user);
        repository.save(holiday);
        log.info("Successfully created holiday");
        return holidayMapToResponse.viewHoliday(holiday);
    }

    public HolidayResponse update(Long id, HolidayRequest holidayRequest) {
        Holiday holiday = repository.findById(id).orElseThrow(NoSuchElementException::new);
        holidayMapToRequest.update(holiday, holidayRequest);
        log.info("Successfully modified");
        return holidayMapToResponse.viewHoliday(repository.save(holiday));
    }

    public HolidayResponse findById(Long id) {
        Holiday holiday = repository.findById(id).orElseThrow(NoSuchElementException::new);
        log.info("Get holiday with id: " + id);
        return holidayMapToResponse.viewHoliday(holiday);
    }

    public String deleteById(Long id) {
        Holiday holiday = repository.findById(id).orElseThrow(NoSuchElementException::new);
        repository.deleteById(holiday.getId());
        log.info("Holiday successfully deleted!");
        return "Holiday successfully deleted!";
    }

    public List<HolidayResponse> getHolidays() {
        log.info("Get all holiday");
        return holidayMapToResponse.view(repository.findAll());
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        log.info("User: " + authentication.getName());
        return userRepository.findByEmail(login);
    }

}
