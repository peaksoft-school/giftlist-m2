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
@RequestMapping("/api/charities")
@CrossOrigin
@Tag(name = "Charity API",description = "User can get charity by id, get all charities, create, update or delete charity")
@RequiredArgsConstructor
public class CharityController {

    private final CharityService charityService;
    private final CategoryService categoryService;

    @GetMapping("/{id}")
    @Operation(summary = "Get charity", description = "User can get a charity by id")
    public Charity getCharityById(@PathVariable Long id) {
        return charityService.getCharityById(id);
    }

    @GetMapping
    @Operation(summary = "Get charities", description = "User can get all charities")
    public List<Charity> getAllCharities() {
        return charityService.getAllCharities();
    }

    @PostMapping
    @Operation(summary = "Add charity", description = "User can create a charity")
    public CharityResponse addCharity(@RequestBody CategoryCharity categoryCharity) {
        categoryService.createCategory(categoryCharity.getCategoryRequest());
        return charityService.createCharity(categoryCharity.getCharityRequest());
    }

    @PostMapping("/{id}")
    @Operation(summary = "charity booking", description = "User can book a charity")
    public Charity booking(@PathVariable Long id) {
        return charityService.book(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update charity", description = "User can update a charity by id")
    public CharityResponse updateCharity(@PathVariable Long id,
                                         @RequestBody CategoryCharity categoryCharity) {
        categoryService.updateCategory(categoryCharity.getCharityRequest().getCategoryId(), categoryCharity.getCategoryRequest());
        return charityService.updateCharity(id, categoryCharity.getCharityRequest());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete charity", description = "User can delete a charity by id")
    public String deleteCharity(@PathVariable Long id) {
        return charityService.deleteCharity(id);
    }

}
