package kg.giftlist.giftlistm2.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import kg.giftlist.giftlistm2.controller.payload.BookingResponse;
import kg.giftlist.giftlistm2.controller.payload.BookingWishListResponse;
import kg.giftlist.giftlistm2.db.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/booking")
@CrossOrigin
@RequiredArgsConstructor
@Tag(name = "User API", description = "booking")
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("charity")
    public List<BookingResponse> getAllCharityBooking() {
        return bookingService.getAllCharityBookings();
    }

    @GetMapping("wishlist")
    public List<BookingWishListResponse> getAllWishListBooking() {
        return bookingService.getAllWishListBookings();
    }

    @GetMapping("{id}")
    public BookingResponse getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }

}
