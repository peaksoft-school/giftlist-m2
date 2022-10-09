package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.controller.payload.CharityRequest;
import kg.giftlist.giftlistm2.controller.payload.CharityResponse;
import kg.giftlist.giftlistm2.db.entity.Booking;
import kg.giftlist.giftlistm2.db.entity.Category;
import kg.giftlist.giftlistm2.db.entity.Charity;
import kg.giftlist.giftlistm2.db.repository.BookingRepository;
import kg.giftlist.giftlistm2.db.repository.CategoryRepository;
import kg.giftlist.giftlistm2.db.repository.CharityRepository;
import kg.giftlist.giftlistm2.db.repository.UserRepository;
import kg.giftlist.giftlistm2.enums.Condition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CharityService {

    private final CharityRepository charityRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final BookingRepository bookingRepository;

    public Charity book(Long id) {
        Charity charity = charityRepository.findById(id).get();
        Booking booking = new Booking();
        booking.setId(booking.getId());
        booking.setCharity(charity);
        booking.setUserId(charity.getUser());
        bookingRepository.save(booking);
        return charity;
    }

    public Charity getCharityById(Long id) {
        return charityRepository.findById(id).get();
    }

    public List<Charity> getAllCharities() {
        return charityRepository.findAll();
    }

    public CharityResponse createCharity(CharityRequest request) {
        Charity charity = new Charity();
        Category category = categoryRepository.findById(request.getCategoryId()).get();
        charity.setGiftName(request.getGiftName());
        charity.setUser(userRepository.findById(request.getUserId()).get());
        charity.setCondition(Condition.valueOf(request.getCondition()));
        charity.setCategory(category);
        charity.setImage(request.getImage());
        charity.setDescription(request.getDescription());
        charity.setCreatedDate(LocalDate.now());
        charityRepository.save(charity);
        return mapToResponse(charity);
    }

    public CharityResponse updateCharity(Long id, CharityRequest request) {
        Charity charity = charityRepository.findById(id).get();
        charity.setGiftName(request.getGiftName());
        charity.setCondition(Condition.valueOf(request.getCondition()));
        charity.setCategory(categoryRepository.findById(request.getCategoryId()).get());
        charity.setImage(request.getImage());
        charity.setDescription(request.getDescription());
        charity.setCreatedDate(LocalDate.now());
        charityRepository.save(charity);
        return mapToResponse(charity);
    }

    public String deleteCharity(Long id) {
//        Booking booking1 = bookingRepository.findById(id).get();
//        List<Booking> bookings = new ArrayList<>((Collection) booking1);
//        for (Booking booking2 : bookings) {
//            booking2.setCharity(null);
//            bookingRepository.save(booking2);
//        }
        charityRepository.deleteById(id);
        return "Charity successfully deleted!";
    }

    public List<CharityResponse> getCharityByUserId(Long userId) {
        List<Charity> charities = charityRepository.getCharityByUserId(userId);
        List<CharityResponse> responses = new ArrayList<>();
        for (Charity charity : charities) {
            responses.add(mapToResponse(charity));
        }
        return responses;
    }

    private CharityResponse mapToResponse(Charity charity) {
        if (charity == null) {
            return null;
        }
        CharityResponse charityResponse = new CharityResponse();
        charityResponse.setId(charity.getId());
        charityResponse.setGiftName(charity.getGiftName());
        charityResponse.setUserId(charity.getUser().getId());
        charityResponse.setFirstName(charity.getUser().getFirstName());
        charityResponse.setLastName(charity.getUser().getLastName());
        charityResponse.setCondition(charity.getCondition());
        charityResponse.setCategory(charity.getCategory());
        charityResponse.setImage(charity.getImage());
        charityResponse.setDescription(charity.getDescription());
        charityResponse.setCreatedDate(LocalDate.now());
        return charityResponse;
    }

}
