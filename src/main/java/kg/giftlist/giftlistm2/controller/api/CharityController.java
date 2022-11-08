package kg.giftlist.giftlistm2.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.giftlist.giftlistm2.controller.payload.*;
import kg.giftlist.giftlistm2.db.service.CharityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/charities")
@CrossOrigin
@Tag(name = "Charity API", description = "User can get charity by id, get all charities, create, update or delete charity")
@SecurityRequirement(name = "Authorization")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('USER')")
public class CharityController {

    private final CharityService charityService;

    @Operation(summary = "Get charity", description = "Getting a charity by id")
    @GetMapping("{id}")
    public CharityResponse getCharityById(@PathVariable Long id) {
        return charityService.getCharityById(id);
    }

    @Operation(summary = "Get charities", description = "Getting all charities")
    @GetMapping
    public List<CharityResponse> getAllCharities() {
        return charityService.getAllCharities();
    }

    @Operation(summary = "Add charity", description = "Creating a charity")
    @PostMapping
    public CharityResponse addCharity(@RequestBody CharityRequest charityRequest) {
        return charityService.createCharity(charityRequest);
    }

    @Operation(summary = "Charity booking", description = "Booking a charity")
    @PostMapping("{id}")
    public String booking(@PathVariable Long id) {
        return charityService.book(id);
    }

    @Operation(summary = "Charity unbooking", description = "Unbooking a charity(deleting a book by id which contains charity)")
    @DeleteMapping("cancel/{id}")
    public String unBook(@PathVariable Long id) {
        return charityService.unBook(id);
    }

    @Operation(summary = "Update charity", description = "Charity updating by id")
    @PutMapping("{id}")
    public CharityResponse updateCharity(@PathVariable Long id,
                                         @RequestBody CharityRequest charityRequest) {
        return charityService.updateCharity(id, charityRequest);
    }

    @Operation(summary = "Delete charity", description = "Deleting a charity by id")
    @DeleteMapping("{id}")
    public String deleteCharity(@PathVariable Long id) {
        return charityService.deleteCharity(id);
    }

    @Operation(summary = "Admin gets a charity", description = "Admin can get a charity by id")
    @GetMapping("charity/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public CharityResponse getCharityByAdmin(@PathVariable Long id) {
        return charityService.getCharityByAdmin(id);
    }

    @Operation(summary = "Admin gets all charities", description = "Admin can get all charities")
    @GetMapping("charity")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<CharityResponse> getAllCharitiesByAdmin() {
        return charityService.getAllCharitiesByAdmin();
    }

    @Operation(summary = "Block charity", description = "Admin can block a charity by id")
    @PostMapping("block/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String blockCharity(@PathVariable Long id) {
        return charityService.blockCharityByAdmin(id);
    }

    @Operation(summary = "Unblock charity", description = "Admin can unblock a charity by id")
    @PostMapping("unblock/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String unBlockCharity(@PathVariable Long id) {
        return charityService.unBlockCharityByAdmin(id);
    }

}
