package kg.giftlist.giftlistm2.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.giftlist.giftlistm2.controller.payload.BookingResponse;
import kg.giftlist.giftlistm2.controller.payload.BookingWishListResponse;
import kg.giftlist.giftlistm2.db.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/booking")
@CrossOrigin
@RequiredArgsConstructor
@Tag(name = "Booking Api", description = "User can get all wish list, get all charity in the book and get book by book id")
@SecurityRequirement(name = "Authorization")
@PreAuthorize("hasAuthority('USER')")
public class BookingController {

    private final BookingService bookingService;

    @Operation(summary = "Get all charity book", description = "Getting all charity book")
    @GetMapping("charity")
    public List<BookingResponse> getAllCharityBooking() {
        return bookingService.getAllCharityBookings();
    }

    @Operation(summary = "Get all wish list book", description = "Getting all wish list book")
    @GetMapping("wishlist")
    public List<BookingWishListResponse> getAllWishListBooking() {
        return bookingService.getAllWishListBookings();
    }

    @Operation(summary = "Get book by id", description = "Getting book by book id")
    @GetMapping("{id}")
    public BookingResponse getBookingById(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }

}
