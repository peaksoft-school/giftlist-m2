package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.controller.payload.response.BookingResponse;
import kg.giftlist.giftlistm2.controller.payload.response.BookingWishListResponse;
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

@Slf4j
@Service
@RequiredArgsConstructor
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
        log.info("Get all booked charities");
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
        log.info("Get all booked gifts");
        return viewWishList(booking);

    }

    public BookingResponse getBookingById(Long id) {
        User user = getAuthenticatedUser();
        Booking booking = bookingRepository.findById(id).orElseThrow(() ->
                new BookingNotFoundException("Not found booking with book id: " + id));
        if (user.getBookings().contains(booking)) {
            log.info("get a book with id: " + booking.getId());
            return bookingMapper.bookingResponse(booking);
        } else {
            log.error("Booking not found");
            throw new BookingNotFoundException("Booking not found in user id: " + user.getId());
        }
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        log.info("User: " + authentication.getName());
        return userRepository.findByEmail(login);
    }

}
