package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.controller.payload.CharityRequest;
import kg.giftlist.giftlistm2.controller.payload.CharityResponse;
import kg.giftlist.giftlistm2.db.entity.*;
import kg.giftlist.giftlistm2.db.repository.*;
import kg.giftlist.giftlistm2.enums.Condition;
import kg.giftlist.giftlistm2.exception.EmptyValueException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CharityService {

    private final CharityRepository charityRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final BookingRepository bookingRepository;
    private final SubcategoryRepository subcategoryRepository;

    public String book(Long id) throws RuntimeException {
        Charity charity = charityRepository.findById(id).get();
        Optional<Booking> booking = bookingRepository.findById(charity.getId());
        boolean notBooked = booking.isEmpty();
        if (notBooked) {
            Booking booking1 = new Booking();
            User user = getAuthenticatedUser();
            booking1.setId(booking1.getId());
            booking1.setCharity(charity);
            booking1.setUserId(user);
            bookingRepository.save(booking1);
            return "You have successfully booked this charity";
        } else {
            throw new EmptyValueException("This charity is already booked");
        }
    }

    public String deleteCharity(Long id) throws RuntimeException {
        if (charityRepository.findById(id).isEmpty()) {
            throw new EmptyValueException("The charity with id " + id + " already deleted!");
        } else {
            charityRepository.deleteById(id);
            return "Charity successfully deleted!";
        }

    }

    public Charity getCharityById(Long id) {
        return charityRepository.findById(id).get();
    }

    public List<Charity> getAllCharities() {
        return charityRepository.findAll();
    }

    public CharityResponse createCharity(CharityRequest request) {
        Charity charity = new Charity();
        User user = getAuthenticatedUser();
        Category category = categoryRepository.findById(request.getCategoryId()).get();
        if (request.getGiftName().isEmpty()) {
            throw new EmptyValueException("Gift name must not be empty!");
        } else {
            charity.setGiftName(request.getGiftName());
        }
        charity.setUser(user);
        if (request.getCondition().isEmpty()) {
            throw new EmptyValueException("Please, choose a condition from list");
        } else {
            charity.setCondition(Condition.valueOf(request.getCondition()));
        }
        if (request.getCategoryId() == null) {
            throw new EmptyValueException("Please, show a category of the gift");
        } else {
            charity.setCategory(category);
        }
        charity.setImage(request.getImage());
        if (request.getDescription().isEmpty()) {
            throw new EmptyValueException("Please, at least describe your gift shortly");
        } else {
            charity.setDescription(request.getDescription());
        }
        charity.setCreatedDate(LocalDate.now());
        charityRepository.save(charity);
        return mapToResponse(charity);
    }

    public CharityResponse updateCharity(Long id, CharityRequest request) {
        Charity charity = charityRepository.findById(id).get();
        if (!categoryRepository.existsById(id)) {
            throw new EmptyValueException("There is no any charity you have requested!");
        } else {
            if (request.getGiftName().isEmpty()) {
                throw new EmptyValueException("Gift name must not be empty!");
            } else {
                charity.setGiftName(request.getGiftName());
            }
            if (request.getCondition().isEmpty()) {
                throw new EmptyValueException("Please, choose a condition from list");
            } else {
                charity.setCondition(Condition.valueOf(request.getCondition()));
            }
            if (request.getCategoryId() == null) {
                throw new EmptyValueException("Please, show a category of the gift");
            } else {
                charity.setCategory(categoryRepository.findById(request.getCategoryId()).get());
            }
            charity.setImage(request.getImage());
            if (request.getDescription().isEmpty()) {
                throw new EmptyValueException("Please, at least describe your gift shortly");
            } else {
                charity.setDescription(request.getDescription());
            }
            charity.setCreatedDate(LocalDate.now());
            charityRepository.save(charity);
            return mapToResponse(charity);
        }
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

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login);
    }

}
