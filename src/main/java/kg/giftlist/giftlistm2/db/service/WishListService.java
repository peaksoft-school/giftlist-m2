package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.controller.payload.request.WishListRequest;
import kg.giftlist.giftlistm2.controller.payload.response.WishListResponse;
import kg.giftlist.giftlistm2.db.entity.Booking;
import kg.giftlist.giftlistm2.db.entity.Holiday;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.entity.WishList;
import kg.giftlist.giftlistm2.db.repository.*;
import kg.giftlist.giftlistm2.enums.WishListStatus;
import kg.giftlist.giftlistm2.exception.BadCredentialsException;
import kg.giftlist.giftlistm2.exception.EmptyValueException;
import kg.giftlist.giftlistm2.exception.UserNotFoundException;
import kg.giftlist.giftlistm2.exception.WishListExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WishListService {

    private final UserRepository userRepository;
    private final WishListRepository wishListRepository;
    private final HolidayRepository holidayRepository;
    private final BookingRepository bookingRepository;
    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;

    public WishListResponse create(WishListRequest request) {
        User user = getAuthenticatedUser();
        Holiday holiday = holidayRepository.findById(request.getHolidayId()).orElseThrow(NoSuchElementException::new);
        WishList wishList = new WishList();
        if (request.getGiftName() == null) {
            log.error("Wish list name must not be empty!");
            throw new EmptyValueException("Wish list name must not be empty!");
        } else {
            wishList.setGiftName(request.getGiftName());
        }
        wishList.setBlocked(false);
        wishList.setUser(user);
        wishList.setLink(request.getLink());
        wishList.setImage(request.getImage());
        wishList.setHolidays(holiday);
        wishList.setHolidayDate(request.getHolidayDate());
        wishList.setDescription(request.getDescription());
        wishList.setWishListStatus(WishListStatus.NOT_BOOKED);
        wishListRepository.save(wishList);
        List<User> friendList = userRepository.getAllFriendByUserId(user.getId());
        wishList.addNotification(notificationService.wishListNotification(user, friendList, wishList));
        notificationRepository.saveAll(wishList.getNotifications());
        log.info("Successfully created wish list");
        return mapToResponse(wishList);
    }

    public WishListResponse addWishList(Long id) {
        User user = getAuthenticatedUser();
        WishList wishList = wishListRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("WishList not found with id: " + id));
        if (user.getWishLists().contains(wishList)) {
            log.error("You have this wishList");
            throw new WishListExistException("You have this wishList");
        }
        if (wishList.isBlocked()) {
            log.error("This wish list was blocked due to it got a complain. Contact to administration of Giftlist");
            throw new BadCredentialsException("This wish list was blocked due to it got a complain. Contact to administration of Giftlist");
        } else {
            WishListRequest wishListRequest = new WishListRequest();
            wishListRequest.setGiftName(wishList.getGiftName());
            wishListRequest.setDescription(wishList.getDescription());
            wishListRequest.setLink(wishList.getLink());
            wishListRequest.setHolidayDate(wishListRequest.getHolidayDate());
            wishListRequest.setImage(wishList.getImage());
            wishListRequest.setHolidayId(wishList.getHolidays().getId());
            log.info("Successfully added wish list");
            return create(wishListRequest);
        }
    }

    public WishListResponse update(Long id, WishListRequest request) {
        User user = getAuthenticatedUser();
        if (wishListRepository.findById(id).isEmpty()) {
            log.error("There is no any wish list with id " + id);
            throw new EmptyValueException("There is no any wish list with id " + id);
        } else {
            WishList wishList = wishListRepository.findById(id).get();
            if (wishList.isBlocked()) {
                log.error("This wish list was blocked due to it got a complain. Contact to administration of Giftlist");
                throw new BadCredentialsException("This wish list was blocked due to it got a complain. Contact to administration of Giftlist");
            } else {
                if (user.getWishLists().contains(wishList)) {
                    Holiday holiday = holidayRepository.findById(request.getHolidayId()).orElseThrow(NoSuchElementException::new);
                    if (request.getGiftName().isEmpty()) {
                        log.error("Wish list name must not be empty!");
                        throw new EmptyValueException("Wish list name must not be empty!");
                    }
                    wishList.setGiftName(request.getGiftName());
                    wishList.setLink(request.getLink());
                    wishList.setImage(request.getImage());
                    wishList.setHolidays(holiday);
                    wishList.setHolidayDate(request.getHolidayDate());
                    wishList.setDescription(request.getDescription());
                    if (bookingRepository.findById(wishList.getId()).isPresent()) {
                        wishList.setWishListStatus(WishListStatus.BOOKED);
                    }
                    if (bookingRepository.findById(wishList.getId()).isEmpty()) {
                        wishList.setWishListStatus(WishListStatus.NOT_BOOKED);
                    }
                    wishListRepository.save(wishList);
                    log.info("Successfully modified");
                    return mapToResponse(wishList);
                } else {
                    log.error("You have no any wish list with id " + id);
                    throw new EmptyValueException("You have no any wish list with id " + id);
                }
            }
        }
    }

    public String delete(Long id) {
        User user = getAuthenticatedUser();
        if (wishListRepository.findById(id).isEmpty()) {
            log.error("There is no any wish list with id " + id);
            throw new EmptyValueException("There is no any wish list with id " + id);
        } else {
            WishList wishList = wishListRepository.findById(id).get();
            if (wishList.isBlocked()) {
                log.error("This wish list was blocked due to it got a complain. Contact to administration of Giftlist");
                throw new BadCredentialsException("This wish list was blocked due to it got a complain. Contact to administration of Giftlist");
            } else {
                if (user.getWishLists().isEmpty()) {
                    log.error("You have no any wish list with id " + id);
                    throw new EmptyValueException("You have no any wish list with id " + id);
                }
                if (user.getWishLists().contains(wishList)) {
                    log.info("wishList" + wishList.getGiftName());
                    user.getWishLists().remove(wishList);
                    wishListRepository.delete(wishList);
                    log.info("Wish list successfully was deleted!");
                    return "Wish list successfully was deleted!";
                } else {
                    log.error("You have no any wish list with id " + id);
                    throw new EmptyValueException("You have no any wish list with id " + id);
                }
            }
        }
    }

    public WishListResponse getWishListById(Long id) {
        User user = getAuthenticatedUser();
        if (wishListRepository.findById(id).isEmpty()) {
            log.error("There is no any wish list with id " + id);
            throw new EmptyValueException("There is no any wish list with id " + id);
        }
        WishList wishList = wishListRepository.findById(id).get();
        if (user.getWishLists().contains(wishList)) {
            log.info("Get wish list with id: " + id);
            return mapToResponse(wishList);
        } else {
            log.error("You have no any wish list with id " + id);
            throw new EmptyValueException("You have no any wish list with id " + id);
        }
    }

    public List<WishListResponse> getAllWishLists() {
        User user = getAuthenticatedUser();
        List<WishList> wishLists = wishListRepository.getWishByUserId(user.getId());
        log.info("Get all wish list");
        return view(wishLists);
    }

    public String book(Long id) {
        User user = getAuthenticatedUser();
        if (wishListRepository.findById(id).isEmpty()) {
            log.error("There is no wishList with id " + id);
            throw new EmptyValueException("There is no wishList with id " + id);
        }
        WishList wishList = wishListRepository.findById(id).get();
        if (wishList.getWishListStatus().equals(WishListStatus.NOT_BOOKED)) {
            Booking booking1 = new Booking();
            booking1.setId(booking1.getId());
            booking1.setWishList(wishList);
            booking1.setUserId(user);
            bookingRepository.save(booking1);
            wishList.setWishListStatus(WishListStatus.BOOKED);
            wishListRepository.save(wishList);
            log.info("You have successfully booked this wishList");
            return "You have successfully booked this wishList";
        } else {
            log.error("This wishList is already booked");
            throw new BadCredentialsException("This wishList is already booked");
        }
    }

    public String unBook(Long id) {
        User user = getAuthenticatedUser();
        if (bookingRepository.findById(id).isEmpty()) {
            log.error("There is no wish list with id " + id);
            throw new EmptyValueException("There is no wish list with id " + id);
        } else {
            Booking booking = bookingRepository.findById(id).get();
            if (user.getBookings().contains(booking)) {
                user.getBookings().remove(booking);
                bookingRepository.delete(booking);
                booking.getWishList().setWishListStatus(WishListStatus.NOT_BOOKED);
                log.info("You have successfully unbooked this wish list");
                return "You have successfully unbooked this wish list";
            } else {
                throw new BadCredentialsException("This wish list is already unbooked");
            }
        }
    }

    public WishListResponse getWishlistByAdmin(Long id) {
        if (wishListRepository.existsById(id)) {
            WishList wishList = wishListRepository.findById(id).orElseThrow(NoSuchElementException::new);
            log.info("Get wish list with id: " + id);
            return mapToResponse(wishList);
        } else {
            log.error("There is no any wish list with id " + id);
            throw new EmptyValueException("There is no any wish list with id " + id);
        }
    }

    public List<WishListResponse> getAllWishlistsByAdmin() {
        List<WishList> wishLists = wishListRepository.findAll();
        log.info("Get all wish list");
        return view(wishLists);
    }

    public String blockWishlistByAdmin(Long id) {
        if (wishListRepository.findById(id).isEmpty()) {
            log.error("There is no any wish list with id " + id);
            throw new EmptyValueException("There is no any wish list with id " + id);
        } else {
            WishList wishList = wishListRepository.findById(id).get();
            if (wishList.isBlocked()) {
                log.error("You have already blocked the wish list with id " + id);
                throw new BadCredentialsException("You have already blocked the wish list with id " + id);
            } else {
                wishList.setBlocked(true);
                wishListRepository.save(wishList);
                log.info("You have blocked the wish list with id " + id);
                return "You have blocked the wish list with id " + id;
            }
        }
    }

    public String unBlockWishlistByAdmin(Long id) {
        if (wishListRepository.findById(id).isEmpty()) {
            log.error("There is no any wish list with id " + id);
            throw new EmptyValueException("There is no any wish list with id " + id);
        } else {
            WishList wishList = wishListRepository.findById(id).get();
            if (!wishList.isBlocked()) {
                log.error("You have already unblocked the wish list with id " + id);
                throw new BadCredentialsException("You have already unblocked the wish list with id " + id);
            } else {
                wishList.setBlocked(false);
                wishListRepository.save(wishList);
                log.info("You have unblocked the wish list with id " + id);
                return "You have unblocked the wish list with id " + id;
            }
        }
    }

    private WishListResponse mapToResponse(WishList wishList) {
        if (wishList == null) {
            return null;
        }
        WishListResponse wishListResponse = new WishListResponse();
        wishListResponse.setId(wishList.getId());
        wishListResponse.setGiftName(wishList.getGiftName());
        wishListResponse.setUserId(wishList.getUser().getId());
        wishListResponse.setFirstName(wishList.getUser().getFirstName());
        wishListResponse.setLastName(wishList.getUser().getLastName());
        wishListResponse.setLink(wishList.getLink());
        wishListResponse.setImage(wishList.getImage());
        wishListResponse.setHolidayName(wishList.getHolidays().getName());
        wishListResponse.setHolidayDate(wishList.getHolidayDate());
        wishListResponse.setDescription(wishList.getDescription());
        wishListResponse.setWishListStatus(wishList.getWishListStatus());
        return wishListResponse;
    }

    public List<WishListResponse> view(List<WishList> wishLists) {
        List<WishListResponse> responses = new ArrayList<>();
        for (WishList wishList : wishLists) {
            responses.add(mapToResponse(wishList));
        }
        return responses;
    }

    public List<WishListResponse> getWishesForFeed() {
        User user = getAuthenticatedUser();
        List<WishList> allFriendWishes = wishListRepository.getAllFriendWishes(user.getId());
        List<WishList> allWishes = wishListRepository.getAllWishes();
        List<WishList> sortedWishes = new ArrayList<>(allFriendWishes);
        allWishes.removeAll(allFriendWishes);
        sortedWishes.addAll(allWishes);
        log.info("Get wishes for feed");
        return view(sortedWishes);
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        log.info("User: " + authentication.getName());
        return userRepository.findByEmail(login);
    }

}
