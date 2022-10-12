package kg.giftlist.giftlistm2.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.giftlist.giftlistm2.controller.payload.*;
import kg.giftlist.giftlistm2.db.entity.Charity;
import kg.giftlist.giftlistm2.db.service.CategoryService;
import kg.giftlist.giftlistm2.db.service.CharityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/charities")
@CrossOrigin
@Tag(name = "Charity API", description = "User can get charity by id, get all charities, create, update or delete charity")
@RequiredArgsConstructor
public class CharityController {

    private final CharityService charityService;
    private final CategoryService categoryService;

    @Operation(summary = "Get charity", description = "User can get a charity by id")
    @GetMapping("{id}")
    public Charity getCharityById(@PathVariable Long id) {
        return charityService.getCharityById(id);
    }

    @Operation(summary = "Get charities", description = "User can get all charities")
    @GetMapping
    public List<Charity> getAllCharities() {
        return charityService.getAllCharities();
    }

    @Operation(summary = "Add charity", description = "Authenticated user can create a charity")
    @PostMapping
    public CharityResponse addCharity(@RequestBody CategoryCharity categoryCharity) {
        categoryService.createCategory(categoryCharity.getCategoryRequest());
        return charityService.createCharity(categoryCharity.getCharityRequest());
    }

    @Operation(summary = "charity booking", description = "Authenticated user can book a charity")
    @PostMapping("{id}")
    public String booking(@PathVariable Long id) {
        try {
            return charityService.book(id);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @Operation(summary = "Update charity", description = "User can update a charity by id")
    @PutMapping("{id}")
    public CharityResponse updateCharity(@PathVariable Long id,
                                         @RequestBody CategoryCharity categoryCharity) {
        categoryService.updateCategory(categoryCharity.getCharityRequest().getCategoryId(), categoryCharity.getCategoryRequest());
        return charityService.updateCharity(id, categoryCharity.getCharityRequest());
    }

    @Operation(summary = "Delete charity", description = "User can delete a charity by id")
    @DeleteMapping("{id}")
    public String deleteCharity(@PathVariable Long id) {
        try {
            return charityService.deleteCharity(id);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

}
