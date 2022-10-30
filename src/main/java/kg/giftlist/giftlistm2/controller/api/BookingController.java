package kg.giftlist.giftlistm2.controller.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import kg.giftlist.giftlistm2.controller.payload.BookingResponse;
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

    @GetMapping
    public List<BookingResponse> getAllBooking(){
        return bookingService.getAllBookings();
    }
    @GetMapping("{id}")
    public BookingResponse getBookingById(@PathVariable Long id){
        return bookingService.getBookingById(id);
    }
}
