package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.controller.payload.CharityResponse;
import kg.giftlist.giftlistm2.controller.payload.WishListRequest;
import kg.giftlist.giftlistm2.controller.payload.WishListResponse;
import kg.giftlist.giftlistm2.db.entity.*;
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

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class WishListService {

    private final UserRepository userRepository;
    private final WishListRepository wishListRepository;
    private final HolidayRepository holidayRepository;
    private final BookingRepository bookingRepository;
    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;

    public WishListResponse create(WishListRequest request) {
        User user = getAuthenticatedUser();
        Holiday holiday = holidayRepository.findById(request.getHolidayId()).get();
        WishList wishList = new WishList();
        wishList.setBlocked(false);
        if (request.getGiftName().isEmpty()) {
            throw new EmptyValueException("Wish list name must not be empty!");
        } else {
            wishList.setGiftName(request.getGiftName());
        }
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
        return mapToResponse(wishList);
    }

    public WishListResponse addWishList(Long id) {
        User user = getAuthenticatedUser();
        WishList wishList = wishListRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("WishList not found with id: " + id));
        if (user.getWishLists().contains(wishList)) {
            throw new WishListExistException("You have this wishList");
        }
        WishListRequest wishListRequest = new WishListRequest();
        wishListRequest.setGiftName(wishList.getGiftName());
        wishListRequest.setDescription(wishList.getDescription());
        wishListRequest.setLink(wishList.getLink());
        wishListRequest.setHolidayDate(wishListRequest.getHolidayDate());
        wishListRequest.setImage(wishList.getImage());
        wishListRequest.setHolidayId(wishList.getHolidays().getId());
        return create(wishListRequest);
    }

    public WishListResponse update(Long id, WishListRequest request) {
        User user = getAuthenticatedUser();
        if (wishListRepository.findById(id).isEmpty()) {
            throw new EmptyValueException("There is no any wish list with id " + id);
        } else {
            WishList wishList = wishListRepository.findById(id).get();
            if (wishList.isBlocked()) {
                throw new BadCredentialsException("Your wish list was blocked due to it got a complain. Contact to administration of Giftlist");
            } else {
                if (user.getWishLists().contains(wishList)) {
                    Holiday holiday = holidayRepository.findById(request.getHolidayId()).get();
                    if (request.getGiftName().isEmpty()) {
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
                    return mapToResponse(wishList);
                } else {
                    throw new EmptyValueException("You have no any wish list with id " + id);
                }
            }
        }
    }

    public String delete(Long id) {
        User user = getAuthenticatedUser();
        if (wishListRepository.findById(id).isEmpty()) {
            throw new EmptyValueException("There is no any wish list with id " + id);
        } else {
            WishList wishList = wishListRepository.findById(id).get();
            if (wishList.isBlocked()) {
                throw new BadCredentialsException("Your wish list was blocked due to it got a complain. Contact to administration of Giftlist");
            } else {
                if (user.getWishLists().isEmpty()) {
                    throw new EmptyValueException("You have no any wish list with id " + id);
                }
                if (user.getWishLists().contains(wishList)) {
                    log.info("wishList" + wishList.getGiftName());
                    user.getWishLists().remove(wishList);
                    wishListRepository.delete(wishList);
                } else {
                    throw new EmptyValueException("You have no any wish list with id " + id);
                }
                return "Wish list successfully was deleted!";
            }
        }
    }

    public WishListResponse getWishListById(Long id) {
        User user = getAuthenticatedUser();
        if (wishListRepository.findById(id).isEmpty()) {
            throw new EmptyValueException("There is no any wish list with id " + id);
        } else {
            WishList wishList = wishListRepository.findById(id).get();
            if (user.getWishLists().contains(wishList)) {
                return mapToResponse(wishList);
            } else {
                throw new EmptyValueException("You have no any wish list with id " + id);
            }
        }
    }

    public List<WishListResponse> getAllWishLists() {
        User user = getAuthenticatedUser();
        if (user.getWishLists().isEmpty()) {
            throw new EmptyValueException("There are no any wish lists");
        }
        List<WishList> wishLists = wishListRepository.getWishListByUserId(user.getId());
        return view(wishLists);
    }

    public String book(Long id) {
        User user = getAuthenticatedUser();
        if (wishListRepository.findById(id).isEmpty()) {
            throw new EmptyValueException("There is no wishList with id " + id);
        } else {
            WishList wishList = wishListRepository.findById(id).get();
            if (wishList.isBlocked()) {
                throw new BadCredentialsException("This wish list was blocked due to it got a complain. Contact to administration of Giftlist");
            } else {
                if (wishList.getWishListStatus().equals(WishListStatus.NOT_BOOKED)) {
                    Booking booking1 = new Booking();
                    booking1.setId(booking1.getId());
                    booking1.setWishList(wishList);
                    booking1.setUserId(user);
                    bookingRepository.save(booking1);
                    wishList.setWishListStatus(WishListStatus.BOOKED);
                    wishListRepository.save(wishList);
                    return "You have successfully booked this wishList";
                } else {
                    throw new BadCredentialsException("This wishList is already booked");
                }
            }
        }
    }

    public String unBook(Long id) {
        User user = getAuthenticatedUser();
        if (bookingRepository.findById(id).isEmpty()) {
            throw new EmptyValueException("There is no wish list with id " + id);
        } else {
            Booking booking = bookingRepository.findById(id).get();
            if (user.getBookings().contains(booking)) {
                user.getBookings().remove(booking);
                bookingRepository.delete(booking.getId());
                booking.getWishList().setWishListStatus(WishListStatus.NOT_BOOKED);
                return "You have successfully unbooked this wish list";
            } else {
                throw new BadCredentialsException("This wish list is already unbooked");
            }
        }
    }

    public WishListResponse getWishListByAdmin(Long id) {
        if (wishListRepository.existsById(id)) {
            WishList wishList = wishListRepository.findById(id).get();
            return mapToResponse(wishList);
        } else {
            throw new EmptyValueException("There is no any wish list with id " + id);
        }
    }

    public List<WishListResponse> getAllWishlistsByAdmin() {
        if (wishListRepository.findAll().isEmpty()) {
            throw new EmptyValueException("There is no any wish list");
        } else {
            List<WishList> wishLists = wishListRepository.findAll();
            return view(wishLists);
        }
    }

    public String blockWishlistByAdmin(Long id) {
        if (wishListRepository.findById(id).isEmpty()) {
            throw new EmptyValueException("There is no any wih list with id " + id);
        } else {
            WishList wishList = wishListRepository.findById(id).get();
            if (wishList.isBlocked()) {
                throw new BadCredentialsException("You have already blocked the wish list with id " + id);
            } else {
                wishList.setBlocked(true);
                wishListRepository.save(wishList);
                return "You have blocked the wish list with id " + id;
            }
        }
    }

    public String unBlockWishlistByAdmin(Long id) {
        if (wishListRepository.findById(id).isEmpty()) {
            throw new EmptyValueException("There is no any wih list with id " + id);
        } else {
            WishList wishList = wishListRepository.findById(id).get();
            if (!wishList.isBlocked()) {
                throw new BadCredentialsException("You have already unblocked the wish list with id " + id);
            } else {
                wishList.setBlocked(false);
                wishListRepository.save(wishList);
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

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login);
    }

}
