package kg.giftlist.giftlistm2.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.giftlist.giftlistm2.controller.payload.ComplainedWishListResponse;
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
    public String complain(@PathVariable Long id,
                           @RequestBody ComplaintRequest request) {
        return complaintService.complaint(id, request);
    }

    @Operation(summary = "Get wish list complaint", description = "Getting a wish list complaint by id")
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ComplainedWishListResponse getById(@PathVariable Long id) {
        return complaintService.getById(id);
    }

    @Operation(summary = "Get all wish list complaints", description = "Getting all wish list complaints")
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List <ComplainedWishListResponse> getAllWishListComplaints() {
        return complaintService.getAllComplainedWishLists();
    }

}
