package kg.giftlist.giftlistm2.mapper;

import jdk.jfr.Category;
import kg.giftlist.giftlistm2.controller.payload.BookingResponse;
import kg.giftlist.giftlistm2.db.entity.Booking;
import kg.giftlist.giftlistm2.exception.BookingNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class BookingMapper {

    public BookingResponse bookingResponse(Booking booking) {
        if (booking == null) {
            throw new BookingNotFoundException("Not found booking");
        }
        BookingResponse bookingResponse = new BookingResponse();
        bookingResponse.setId(booking.getId());
        bookingResponse.setFirstName(booking.getCharity().getUser().getFirstName());
        bookingResponse.setLastName(booking.getCharity().getUser().getLastName());
        bookingResponse.setGitName(booking.getCharity().getGiftName());
        bookingResponse.setCondition(booking.getCharity().getCondition());
        bookingResponse.setCreated(booking.getCharity().getCreatedDate());
        return bookingResponse;
    }

    public BookingResponse bookingWishListResponse(Booking booking) {
        if (booking == null) {
            throw new BookingNotFoundException("Not found booking");
        }
        BookingResponse bookingResponse = new BookingResponse();
        bookingResponse.setId(booking.getId());
        bookingResponse.setFirstName(booking.getCharity().getUser().getFirstName());
        bookingResponse.setLastName(booking.getCharity().getUser().getLastName());
        bookingResponse.setGitName(booking.getCharity().getGiftName());
        bookingResponse.setCondition(booking.getCharity().getCondition());
        bookingResponse.setCreated(booking.getCharity().getCreatedDate());
        return bookingResponse;
    }

}
