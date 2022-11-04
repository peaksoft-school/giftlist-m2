package kg.giftlist.giftlistm2.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.giftlist.giftlistm2.controller.payload.WishlistComplaintResponse;
import kg.giftlist.giftlistm2.controller.payload.ComplaintRequest;
import kg.giftlist.giftlistm2.db.service.ComplaintService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/complaints")
@CrossOrigin
@Tag(name = "Complaint API", description = "User can complain posts to admin")
@SecurityRequirement(name = "Authorization")
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintService complaintService;

    @Operation(summary = "Complaining the posts", description = "Complain the posts to admin")
    @PostMapping("{id}")
    public String wishlistComplaint(@PathVariable Long id,
                                    @RequestBody ComplaintRequest request) {
        return complaintService.wishlistComplaint(id, request);
    }

    @Operation(summary = "Get wish list complaint", description = "Getting a wish list complaint by id")
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public WishlistComplaintResponse getWishlistComplaintById(@PathVariable Long id) {
        return complaintService.getWishlistComplaintById(id);
    }

    @Operation(summary = "Get all wish list complaints", description = "Getting all wish list complaints")
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List <WishlistComplaintResponse> getAllWishListComplaints() {
        return complaintService.getAllWishListComplaints();
    }

    @Operation(summary = "Delete wish list complaint", description = "Delete wish list complaint by id")
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteWishlistComplaint(@PathVariable Long id) {
        return complaintService.deleteWishlistComplaint(id);
    }

}
