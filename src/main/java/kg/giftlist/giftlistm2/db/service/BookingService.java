package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.controller.payload.BookingResponse;
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


    public List<BookingResponse> view(List<Booking> bookingList) {
        List<BookingResponse> responses = new ArrayList<>();
        for (Booking booking : bookingList) {
            responses.add(bookingMapper.bookingResponse(booking));
        }
        return responses;
    }

    public List<BookingResponse> getAllBookings() {
        User user = getAuthenticatedUser();
        List<Booking> booking = bookingRepository.getBookingByUserId(user.getId());
        if (booking.isEmpty()) {
            log.error("Booking not found id {} user " + user.getId());
            throw new BookingNotFoundException("Bookings not found");
        }
        return view(booking);
    }

    public BookingResponse getBookingById(Long id){
        User  user = getAuthenticatedUser();
        Booking booking = bookingRepository.findById(id).orElseThrow( ()->new BookingNotFoundException("Not found"));
        if (user.getBookings().contains(booking)){
            return bookingMapper.bookingResponse(booking);
        }
        else {
            throw new BookingNotFoundException("Booking not found in user id: "+user.getId());
        }
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login);
    }

}
