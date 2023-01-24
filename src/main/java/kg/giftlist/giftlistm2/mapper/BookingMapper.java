package kg.giftlist.giftlistm2.mapper;

import kg.giftlist.giftlistm2.controller.payload.response.BookingResponse;
import kg.giftlist.giftlistm2.controller.payload.response.BookingWishListResponse;
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
        bookingResponse.setCharityImage(booking.getCharity().getImage());
        bookingResponse.setCondition(booking.getCharity().getCondition());
        bookingResponse.setCreatedAt(LocalDate.now());
        return bookingResponse;
    }

    public BookingWishListResponse bookingWishListResponse(Booking booking) {
        if (booking == null) {
            throw new BookingNotFoundException("Not found booking");
        }
        BookingWishListResponse bookingResponse = new BookingWishListResponse();
        bookingResponse.setId(booking.getId());
        bookingResponse.setFirstName(booking.getWishList().getUser().getFirstName());
        bookingResponse.setLastName(booking.getWishList().getUser().getLastName());
        bookingResponse.setWishListGiftName(booking.getWishList().getGiftName());
        bookingResponse.setHolidayName(booking.getWishList().getHolidays().getName());
        bookingResponse.setCreatedAt(LocalDate.now());
        return bookingResponse;
    }

}
