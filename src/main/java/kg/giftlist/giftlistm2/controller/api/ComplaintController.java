package kg.giftlist.giftlistm2.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.giftlist.giftlistm2.controller.payload.CharityComplaintResponse;
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
@PreAuthorize("hasAuthority('ADMIN')")
public class ComplaintController {

    private final ComplaintService complaintService;

    @Operation(summary = "Complaining the wish list posts to admin", description = "Creating a complain by wish list id")
    @PostMapping("wishlist/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public String createWishlistComplaint(@PathVariable Long id,
                                          @RequestBody ComplaintRequest request) {
        return complaintService.createWishlistComplaint(id, request);
    }

    @Operation(summary = "Complaining the charity posts", description = "Creating a complain by charity id")
    @PostMapping("charity/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public String createCharityComplaint(@PathVariable Long id,
                                         @RequestBody ComplaintRequest request) {
        return complaintService.createCharityComplaint(id, request);
    }

    @Operation(summary = "Get wish list complaint", description = "Getting a wish list complaint by id")
    @GetMapping("wishlist/{id}")
    public WishlistComplaintResponse getWishlistComplaintById(@PathVariable Long id) {
        return complaintService.getWishlistComplaintById(id);
    }

    @Operation(summary = "Get charity complaint", description = "Getting a charity complaint by id")
    @GetMapping("charity/{id}")
    public CharityComplaintResponse getCharityComplaintById(@PathVariable Long id) {
        return complaintService.getCharityComplaintById(id);
    }

    @Operation(summary = "Get all wish list complaints", description = "Getting all wish list complaints")
    @GetMapping("wishlist/all")
    public List<WishlistComplaintResponse> getAllWishListComplaints() {
        return complaintService.getAllWishListComplaints();
    }

    @Operation(summary = "Get all charity complaints", description = "Getting all charity complaints")
    @GetMapping("charity/all")
    public List<CharityComplaintResponse> getAllCharityComplaints() {
        return complaintService.getAllCharityComplaints();
    }

    @Operation(summary = "Delete wish list complaint", description = "Deleting wish list complaint by id")
    @DeleteMapping("wishlist/{id}")
    public String deleteWishlistComplaint(@PathVariable Long id) {
        return complaintService.deleteWishlistComplaint(id);
    }

    @Operation(summary = "Delete charity complaint", description = "Deleting charity complaint by id")
    @DeleteMapping("charity/{id}")
    public String deleteCharityComplaint(@PathVariable Long id) {
        return complaintService.deleteCharityComplaint(id);
    }

    @Operation(summary = "Block charity", description = "Blocking charity by id")
    @PostMapping("charity/block/{id}")
    public String blockCharity(@PathVariable Long id) {
        return complaintService.blockCharity(id);
    }

    @Operation(summary = "Block wish list", description = "Blocking wish list by id")
    @PostMapping("wishlist/block/{id}")
    public String blockWishlist(@PathVariable Long id) {
        return complaintService.blockWishlist(id);
    }

    @Operation(summary = "Unblock charity", description = "Unblocking charity by id")
    @PostMapping("charity/unblock/{id}")
    public String unBlockCharity(@PathVariable Long id) {
        return complaintService.unBlockCharity(id);
    }

    @Operation(summary = "Unblock wish list", description = "Unblocking wish list by id")
    @PostMapping("wishlist/unblock/{id}")
    public String unBlockWishlist(@PathVariable Long id) {
        return complaintService.unBlockWishlist(id);
    }

}
