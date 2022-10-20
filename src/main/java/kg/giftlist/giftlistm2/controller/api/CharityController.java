package kg.giftlist.giftlistm2.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.giftlist.giftlistm2.controller.payload.*;
import kg.giftlist.giftlistm2.db.entity.Charity;
import kg.giftlist.giftlistm2.db.service.CharityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/charities")
@CrossOrigin
@Tag(name = "Charity API", description = "User can get charity by id, get all charities, create, update or delete charity")
@SecurityRequirement(name = "Authorization")
@RequiredArgsConstructor
public class CharityController {

    private final CharityService charityService;

    @Operation(summary = "Get charity", description = "Getting a charity by id")
    @GetMapping("{id}")
    public Charity getCharityById(@PathVariable Long id) {
        return charityService.getCharityById(id);
    }

    @Operation(summary = "Get charities", description = "Getting all charities")
    @GetMapping
    public List<Charity> getAllCharities() {
        return charityService.getAllCharities();
    }

    @Operation(summary = "Add charity", description = "Creating a charity")
    @PostMapping
    public CharityResponse addCharity(@RequestBody CharityRequest charityRequest) {
        return charityService.createCharity(charityRequest);
    }

    @Operation(summary = "charity booking", description = "Booking a charity")
    @PostMapping("{id}")
    public String booking(@PathVariable Long id) {
        return charityService.book(id);
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

}
