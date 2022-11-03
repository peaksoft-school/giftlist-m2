package kg.giftlist.giftlistm2.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.giftlist.giftlistm2.controller.payload.ComplaintRequest;
import kg.giftlist.giftlistm2.db.service.ComplaintService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

}
