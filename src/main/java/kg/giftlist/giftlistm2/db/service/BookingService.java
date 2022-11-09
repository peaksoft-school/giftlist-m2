package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.controller.payload.BookingResponse;
import kg.giftlist.giftlistm2.controller.payload.BookingWishListResponse;
import kg.giftlist.giftlistm2.db.entity.Booking;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.repository.BookingRepository;
import kg.giftlist.giftlistm2.db.repository.UserRepository;
import kg.giftlist.giftlistm2.exception.BookingNotFoundException;
import kg.giftlist.giftlistm2.mapper.BookingMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingService {

    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;


    public List<BookingResponse> viewCharity(List<Booking> bookingList) {
        List<BookingResponse> responses = new ArrayList<>();
        for (Booking booking : bookingList) {
            responses.add(bookingMapper.bookingResponse(booking));
        }
        return responses;
    }

    public List<BookingResponse> getAllCharityBookings() {
        User user = getAuthenticatedUser();
        List<Booking> booking = bookingRepository.getAllCharityBooking(user.getId());
        if (booking.isEmpty()) {
            log.error("Booking not found");
            throw new BookingNotFoundException("Bookings not found");
        }
        return viewCharity(booking);
    }

    public List<BookingWishListResponse> viewWishList(List<Booking> bookingList) {
        List<BookingWishListResponse> responses = new ArrayList<>();
        for (Booking booking : bookingList) {
            responses.add(bookingMapper.bookingWishListResponse(booking));
        }
        return responses;
    }

    public List<BookingWishListResponse> getAllWishListBookings() {
        User user = getAuthenticatedUser();
        List<Booking> booking = bookingRepository.getAllWishListBooking(user.getId());
        if (booking.isEmpty()) {
            log.error("Booking not found");
            throw new BookingNotFoundException("Bookings not found");
        }
        return viewWishList(booking);

    }

    public BookingResponse getBookingById(Long id) {
        User user = getAuthenticatedUser();
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new BookingNotFoundException("Not found"));
        if (user.getBookings().contains(booking)) {
            return bookingMapper.bookingResponse(booking);
        } else {
            throw new BookingNotFoundException("Booking not found in user id: " + user.getId());
        }
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login);
    }

}
