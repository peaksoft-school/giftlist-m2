package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.controller.payload.CharityRequest;
import kg.giftlist.giftlistm2.controller.payload.CharityResponse;
import kg.giftlist.giftlistm2.db.entity.*;
import kg.giftlist.giftlistm2.db.repository.*;
import kg.giftlist.giftlistm2.enums.CharityStatus;
import kg.giftlist.giftlistm2.enums.Condition;
import kg.giftlist.giftlistm2.exception.BadCredentialsException;
import kg.giftlist.giftlistm2.exception.EmptyValueException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CharityService {

    private final CharityRepository charityRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final BookingRepository bookingRepository;
    private final SubcategoryRepository subcategoryRepository;

    public String book(Long id) {
        User user = getAuthenticatedUser();
        if (charityRepository.findById(id).isEmpty()) {
            throw new EmptyValueException("There is no charity with id " + id);
        } else {
            Charity charity = charityRepository.findById(id).get();
            if (charity.isBlocked()) {
                throw new BadCredentialsException("This charity was blocked due to it got a complain. Contact to administration of Giftlist");
            } else {
                if (charity.getCharityStatus().equals(CharityStatus.NOT_BOOKED)) {
                    Booking booking1 = new Booking();
                    booking1.setId(booking1.getId());
                    booking1.setCharity(charity);
                    booking1.setUserId(user);
                    bookingRepository.save(booking1);
                    charity.setCharityStatus(CharityStatus.BOOKED);
                    charityRepository.save(charity);
                    return "You have successfully booked this charity";
                } else {
                    throw new BadCredentialsException("This charity is already booked");
                }
            }
        }
    }

    public String unBook(Long id) {
        User user = getAuthenticatedUser();
        if (bookingRepository.findById(id).isEmpty()) {
            throw new EmptyValueException("There is no charity with id " + id + " to remove from booking");
        } else {
            Booking booking = bookingRepository.findById(id).get();
            if (user.getBookings().contains(booking)) {
                user.getBookings().remove(booking);
                bookingRepository.delete(booking.getId());
                booking.getCharity().setCharityStatus(CharityStatus.NOT_BOOKED);
                return "You have successfully unbooked this charity";
            } else {
                throw new BadCredentialsException("This charity is already unbooked");
            }
        }
    }

    public String deleteCharity(Long id) {
        User user = getAuthenticatedUser();
        if (charityRepository.findById(id).isEmpty()) {
            throw new EmptyValueException("There is no any charity with id " + id);
        }
        Charity charity = charityRepository.findById(id).get();
        if (charity.isBlocked()) {
            throw new BadCredentialsException("Your charity was blocked due to it got a complain. Contact to administration of Giftlist");
        } else {
            if (user.getCharities().isEmpty()) {
                throw new EmptyValueException("You have no any charity with id " + id);
            }
            if (user.getCharities().contains(charity)) {
                user.getCharities().remove(charity);
                charityRepository.deleteById(charity.getId());
                return "Charity successfully was deleted!";
            } else {
                throw new EmptyValueException("You have no any charity with id " + id);
            }
        }
    }

    public CharityResponse getCharityById(Long id) {
        User user = getAuthenticatedUser();
        if (charityRepository.findById(id).isEmpty()) {
            throw new EmptyValueException("There is no any charity with id " + id);
        }
        Charity charities = charityRepository.findById(id).get();
        if (user.getCharities().contains(charities)) {
            return mapToResponse(charities);
        } else {
            throw new EmptyValueException("You have no any charity with id " + id);
        }
    }

    public List<CharityResponse> getAllCharities() {
        User user = getAuthenticatedUser();
        if (user.getCharities().isEmpty()) {
            throw new EmptyValueException("There is no any charities");
        }
        List<Charity> charities = charityRepository.getCharityByUserId(user.getId());
        return view(charities);
    }

    public CharityResponse createCharity(CharityRequest request) {
        User user = getAuthenticatedUser();
        Charity charity = new Charity();
        Category category = categoryRepository.findById(request.getCategoryId()).get();
        Subcategory subcategory = subcategoryRepository.findById(request.getSubcategoryId()).get();
        charity.setBlocked(false);
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
            log.info("Please, show a category of the gift");
            throw new EmptyValueException("Please, show a category of the gift");
        } else {
            charity.setCategory(category);
        }
        if (request.getSubcategoryId() == null) {
            throw new EmptyValueException("Please, show a subcategory of the gift");
        } else {
            charity.setSubcategory(subcategory);
        }
        charity.setImage(request.getImage());
        if (request.getDescription().isEmpty()) {
            throw new EmptyValueException("Please, at least describe your gift shortly");
        } else {
            charity.setDescription(request.getDescription());
        }
        charity.setCreatedDate(LocalDate.now());
        charity.setCharityStatus(CharityStatus.NOT_BOOKED);
        charityRepository.save(charity);
        return mapToResponse(charity);
    }

    public CharityResponse updateCharity(Long id, CharityRequest request) {
        User user = getAuthenticatedUser();
        if (charityRepository.findById(id).isEmpty()) {
            throw new EmptyValueException("There is no any charity with id " + id);
        } else {
            Charity charity = charityRepository.findById(id).get();
            if (charity.isBlocked()) {
                throw new BadCredentialsException("Your charity was blocked due to it got a complain. Contact to administration of Giftlist");
            } else {
                if (user.getCharities().contains(charity)) {
                    Category category = categoryRepository.findById(request.getCategoryId()).get();
                    Subcategory subcategory = subcategoryRepository.findById(request.getSubcategoryId()).get();
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
                        charity.setCategory(category);
                    }
                    if (request.getSubcategoryId() == null) {
                        throw new EmptyValueException("Please, show a subcategory of the gift");
                    } else {
                        charity.setSubcategory(subcategory);
                    }
                    charity.setImage(request.getImage());
                    if (request.getDescription().isEmpty()) {
                        throw new EmptyValueException("Please, at least describe your gift shortly");
                    } else {
                        charity.setDescription(request.getDescription());
                    }
                    charity.setCreatedDate(LocalDate.now());
                    if (bookingRepository.findById(charity.getId()).isPresent()) {
                        charity.setCharityStatus(CharityStatus.BOOKED);
                    }
                    if (bookingRepository.findById(charity.getId()).isEmpty()) {
                        charity.setCharityStatus(CharityStatus.NOT_BOOKED);
                    }
                    charityRepository.save(charity);
                    return mapToResponse(charity);
                } else {
                    throw new EmptyValueException("You have no any charity with id " + id);
                }
            }
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
        charityResponse.setCharityStatus(charity.getCharityStatus());
        charityResponse.setCondition(charity.getCondition());
        charityResponse.setCategory(charity.getCategory().getCategoryName());
        charityResponse.setSubcategory(charity.getSubcategory().getSubcategoryName());
        charityResponse.setImage(charity.getImage());
        charityResponse.setDescription(charity.getDescription());
        charityResponse.setCreatedDate(LocalDate.now());
        return charityResponse;
    }

    public List<CharityResponse> view(List<Charity> charities) {
        List<CharityResponse> responses = new ArrayList<>();
        for (Charity charity : charities) {
            responses.add(mapToResponse(charity));
        }
        return responses;
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login);
    }

}