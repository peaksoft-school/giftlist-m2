package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.controller.payload.request.CharityRequest;
import kg.giftlist.giftlistm2.controller.payload.response.CharityResponse;
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
import java.util.NoSuchElementException;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CharityService {

    private final CharityRepository charityRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final BookingRepository bookingRepository;
    private final SubcategoryRepository subcategoryRepository;
    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;

    public String book(Long id) {
        User user = getAuthenticatedUser();
        if (charityRepository.findById(id).isEmpty()) {
            log.error("There is no charity with id " + id);
            throw new EmptyValueException("There is no charity with id " + id);
        } else {
            Charity charity = charityRepository.findById(id).get();
            if (charity.isBlocked()) {
                log.info("This charity was blocked due to it got a complain. Contact to administration of Giftlist");
                throw new BadCredentialsException("This charity was blocked due to it got a complain. Contact to administration of Giftlist");
            } else {
                if (charity.getCharityStatus().equals(CharityStatus.NOT_BOOKED)) {
                    Booking book = new Booking();
                    book.setId(book.getId());
                    book.setCharity(charity);
                    book.setUserId(user);
                    bookingRepository.save(book);
                    charity.setCharityStatus(CharityStatus.BOOKED);
                    charityRepository.save(charity);
                    charity.addNotification(notificationService.bookedCharity(user, new ArrayList<>(List.of(charity.getUser())), charity));
                    notificationRepository.saveAll(charity.getNotifications());
                    log.info("You have successfully booked this charity");
                    return "You have successfully booked this charity";
                } else {
                    log.error("This charity is already booked");
                    throw new BadCredentialsException("This charity is already booked");
                }
            }
        }
    }

    public String unBook(Long id) {
        User user = getAuthenticatedUser();
        if (bookingRepository.findById(id).isEmpty()) {
            log.error("There is no charity with id " + id + " to remove from booking");
            throw new EmptyValueException("There is no charity with id " + id + " to remove from booking");
        }
        Booking booking = bookingRepository.findById(id).get();
        if (user.getBookings().contains(booking)) {
            user.getBookings().remove(booking);
            bookingRepository.delete(booking);
            booking.getCharity().setCharityStatus(CharityStatus.NOT_BOOKED);
            log.info("You have successfully unbooked this charity");
            return "You have successfully unbooked this charity";
        } else {
            log.info("This charity is already unbooked");
            throw new BadCredentialsException("This charity is already unbooked");
        }
    }

    public String deleteCharity(Long id) {
        User user = getAuthenticatedUser();
        if (charityRepository.findById(id).isEmpty()) {
            log.info("There is no any charity with id: " + id);
            throw new EmptyValueException("There is no any charity with id " + id);
        } else {
            Charity charity = charityRepository.findById(id).get();
            if (charity.isBlocked()) {
                log.error("Your charity was blocked due to it got a complain. Contact to administration of Giftlist");
                throw new BadCredentialsException("Your charity was blocked due to it got a complain. Contact to administration of Giftlist");
            } else {
                if (user.getCharities().isEmpty()) {
                    log.error("You have no any charity with id " + id);
                    throw new EmptyValueException("You have no any charity with id " + id);
                }
                if (user.getCharities().contains(charity)) {
                    user.getCharities().remove(charity);
                    charityRepository.deleteById(charity.getId());
                    log.info("Charity successfully was deleted!");
                    return "Charity successfully was deleted!";
                } else {
                    log.error("You have no any charity with id " + id);
                    throw new EmptyValueException("You have no any charity with id " + id);
                }
            }
        }
    }

    public CharityResponse getCharityById(Long id) {
        User user = getAuthenticatedUser();
        if (charityRepository.findById(id).isEmpty()) {
            log.error("There is no any charity with id " + id);
            throw new EmptyValueException("There is no any charity with id " + id);
        }
        Charity charity = charityRepository.findById(id).get();
        if (user.getCharities().contains(charity)) {
            log.info("get charity by charity id: " + charity.getId());
            return mapToResponse(charity);
        } else {
            log.error("You have no any charity with id " + id);
            throw new EmptyValueException("You have no any charity with id " + id);
        }
    }

    public List<CharityResponse> getAllCharities() {
        User user = getAuthenticatedUser();
        List<Charity> charities = charityRepository.getCharityByUserId(user.getId());
        return view(charities);
    }

    public CharityResponse createCharity(CharityRequest request) {
        User user = getAuthenticatedUser();
        Charity charity = new Charity();
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(NoSuchElementException::new);
        Subcategory subcategory = subcategoryRepository.findById(request.getSubcategoryId()).orElseThrow(NoSuchElementException::new);
        if (request.getGiftName().isEmpty()) {
            throw new EmptyValueException("Gift name must not be empty!");
        } else {
            charity.setGiftName(request.getGiftName());
        }
        charity.setBlocked(false);
        charity.setUser(user);
        if (request.getCondition().isEmpty()) {
            log.error("Please, choose a condition from list");
            throw new EmptyValueException("Please, choose a condition from list");
        } else {
            charity.setCondition(Condition.valueOf(request.getCondition()));
        }
        if (request.getCategoryId() == null) {
            log.error("Please, show a category of the gift");
            throw new EmptyValueException("Please, show a category of the gift");
        } else {
            charity.setCategory(category);
        }
        if (request.getSubcategoryId() == null) {
            log.error("Please, show a subcategory of the gift");
            throw new EmptyValueException("Please, show a subcategory of the gift");
        } else {
            charity.setSubcategory(subcategory);
        }
        charity.setImage(request.getImage());
        if (request.getDescription().isEmpty()) {
            log.error("Please, at least describe your gift shortly");
            throw new EmptyValueException("Please, at least describe your gift shortly");
        } else {
            charity.setDescription(request.getDescription());
        }
        charity.setCreatedAt(LocalDate.now());
        charity.setCharityStatus(CharityStatus.NOT_BOOKED);
        charityRepository.save(charity);
        log.info("Successfully created charity");
        return mapToResponse(charity);
    }

    public CharityResponse updateCharity(Long id, CharityRequest request) {
        User user = getAuthenticatedUser();
        if (charityRepository.findById(id).isEmpty()) {
            log.info("There is no any charity with id " + id);
            throw new EmptyValueException("There is no any charity with id " + id);
        } else {
            Charity charity = charityRepository.findById(id).get();
            if (charity.isBlocked()) {
                throw new BadCredentialsException("Your charity was blocked due to it got a complain. Contact to administration of Giftlist");
            } else {
                if (user.getCharities().contains(charity)) {
                    Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(NoSuchElementException::new);
                    Subcategory subcategory = subcategoryRepository.findById(request.getSubcategoryId()).orElseThrow(NoSuchElementException::new);
                    if (request.getGiftName().isEmpty()) {
                        log.error("Gift name must not be empty!");
                        throw new EmptyValueException("Gift name must not be empty!");
                    } else {
                        charity.setGiftName(request.getGiftName());
                    }
                    if (request.getCondition().isEmpty()) {
                        log.error("Please, choose a condition from list");
                        throw new EmptyValueException("Please, choose a condition from list");
                    } else {
                        charity.setCondition(Condition.valueOf(request.getCondition()));
                    }
                    if (request.getCategoryId() == null) {
                        log.error("Please, show a category of the gift");
                        throw new EmptyValueException("Please, show a category of the gift");
                    } else {
                        charity.setCategory(category);
                    }
                    if (request.getSubcategoryId() == null) {
                        log.error("Please, show a subcategory of the gift");
                        throw new EmptyValueException("Please, show a subcategory of the gift");
                    } else {
                        charity.setSubcategory(subcategory);
                    }
                    charity.setImage(request.getImage());
                    if (request.getDescription().isEmpty()) {
                        log.error("Please, at least describe your gift shortly");
                        throw new EmptyValueException("Please, at least describe your gift shortly");
                    } else {
                        charity.setDescription(request.getDescription());
                    }
                    charity.setCreatedAt(LocalDate.now());
                    if (bookingRepository.findById(charity.getId()).isPresent()) {
                        charity.setCharityStatus(CharityStatus.BOOKED);
                    }
                    if (bookingRepository.findById(charity.getId()).isEmpty()) {
                        charity.setCharityStatus(CharityStatus.NOT_BOOKED);
                    }
                    charityRepository.save(charity);
                    log.info("Successfully modified");
                    return mapToResponse(charity);
                } else {
                    log.error("You have no any charity with id " + id);
                    throw new EmptyValueException("You have no any charity with id " + id);
                }
            }
        }
    }

    public CharityResponse getCharityByAdmin(Long id) {
        if (charityRepository.existsById(id)) {
            Charity charity = charityRepository.findById(id).orElseThrow(NoSuchElementException::new);
            log.info("Get charity with id: " + charity.getId());
            return mapToResponse(charity);
        } else {
            log.error("There is no any charity with id " + id);
            throw new EmptyValueException("There is no any charity with id " + id);
        }
    }

    public List<CharityResponse> getAllCharitiesByAdmin() {
        List<Charity> charities = charityRepository.findAll();
        log.info("Get all charities");
        return view(charities);
    }


    public String blockCharityByAdmin(Long id) {
        if (charityRepository.findById(id).isEmpty()) {
            log.error("There is no any charity with id " + id);
            throw new EmptyValueException("There is no any charity with id " + id);
        } else {
            Charity charity = charityRepository.findById(id).get();
            if (charity.isBlocked()) {
                log.error("You have already blocked the charity with id " + id);
                throw new BadCredentialsException("You have already blocked the charity with id " + id);
            } else {
                charity.setBlocked(true);
                charityRepository.save(charity);
                log.info("You have blocked the charity with id " + id);
                return "You have blocked the charity with id " + id;
            }
        }
    }

    public String unBlockCharityByAdmin(Long id) {
        if (charityRepository.findById(id).isEmpty()) {
            log.error("There is no any charity with id " + id);
            throw new EmptyValueException("There is no any charity with id " + id);
        } else {
            Charity charity = charityRepository.findById(id).get();
            if (!charity.isBlocked()) {
                log.error("You have already unblocked the charity with id " + id);
                throw new BadCredentialsException("You have already unblocked the charity with id " + id);
            } else {
                charity.setBlocked(false);
                charityRepository.save(charity);
                log.info("You have unblocked the charity with id " + id);
                return "You have unblocked the charity with id " + id;
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
        log.info("User: " + authentication.getName());
        return userRepository.findByEmail(login);
    }

}