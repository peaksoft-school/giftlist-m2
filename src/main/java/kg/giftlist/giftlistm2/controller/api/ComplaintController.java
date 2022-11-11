package kg.giftlist.giftlistm2.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.giftlist.giftlistm2.controller.payload.CharityComplaintResponse;
import kg.giftlist.giftlistm2.controller.payload.ComplaintRequest;
import kg.giftlist.giftlistm2.controller.payload.WishlistComplaintResponse;
import kg.giftlist.giftlistm2.db.service.ComplaintService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/complaints")
@CrossOrigin
@Tag(name = "Complaint API", description = "User can complain posts to admin, admin can delete a complain by id, block or unblock the post by id")
@SecurityRequirement(name = "Authorization")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
public class ComplaintController {

    private final ComplaintService complaintService;

    @Operation(summary = "Complain the wish list posts to admin", description = "Creating a complaint by wish list id")
    @PostMapping("wishlist/{id}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String createWishlistComplaint(@PathVariable Long id,
                                          @RequestBody ComplaintRequest request) {
        return complaintService.createWishlistComplaint(id, request);
    }

    @Operation(summary = "Complain the charity posts to admin", description = "Creating a complain by charity id")
    @PostMapping("charity/{id}")
    @PreAuthorize("hasAnyAuthority('USER')")
    public String createCharityComplaint(@PathVariable Long id,
                                         @RequestBody ComplaintRequest request) {
        return complaintService.createCharityComplaint(id, request);
    }

    @Operation(summary = "Get wish list complaint", description = "Admin can get a wish list complaint by id")
    @GetMapping("wishlist/{id}")
    public WishlistComplaintResponse getWishlistComplaintById(@PathVariable Long id) {
        return complaintService.getWishlistComplaintById(id);
    }

    @Operation(summary = "Get charity complaint", description = "Admin can get a charity complaint by id")
    @GetMapping("charity/{id}")
    public CharityComplaintResponse getCharityComplaintById(@PathVariable Long id) {
        return complaintService.getCharityComplaintById(id);
    }

    @Operation(summary = "Get all wish list complaints", description = "Admin can get all wish list complaints")
    @GetMapping("wishlist/all")
    public List<WishlistComplaintResponse> getAllWishListComplaints() {
        return complaintService.getAllWishListComplaints();
    }

    @Operation(summary = "Get all charity complaints", description = "Admin can get all charity complaints")
    @GetMapping("charity/all")
    public List<CharityComplaintResponse> getAllCharityComplaints() {
        return complaintService.getAllCharityComplaints();
    }

    @Operation(summary = "Delete a complaint", description = "Admin can delete a complaint by id")
    @DeleteMapping("{id}")
    public String deleteComplaint(@PathVariable Long id) {
        return complaintService.deleteComplaint(id);
    }

}
