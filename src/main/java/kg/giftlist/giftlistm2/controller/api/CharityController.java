package kg.giftlist.giftlistm2.controller.api;

import kg.giftlist.giftlistm2.controller.payload.*;
import kg.giftlist.giftlistm2.db.entity.Category;
import kg.giftlist.giftlistm2.db.entity.Charity;
import kg.giftlist.giftlistm2.db.service.CategoryService;
import kg.giftlist.giftlistm2.db.service.CharityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/charities")
@CrossOrigin
@RequiredArgsConstructor
public class CharityController {

    private final CharityService charityService;
    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public Charity getCharityById(@PathVariable Long id) {
        return charityService.getCharityById(id);
    }

    @GetMapping
    public List<Charity> getAllCharities() {
        return charityService.getAllCharities();
    }

    @PostMapping
    public CharityResponse addCharity (@RequestBody CategoryCharity categoryCharity) {
        categoryService.createCategory(categoryCharity.getCategoryRequest());
        return charityService.createCharity(categoryCharity.getCharityRequest());
    }

    @PutMapping("/{id}")
    public CharityResponse updateCharity(@PathVariable Long id,
                                         @RequestBody CategoryCharity categoryCharity) {
        categoryService.updateCategory(categoryCharity.getCharityRequest().getCategoryId(), categoryCharity.getCategoryRequest());
        return charityService.updateCharity(id, categoryCharity.getCharityRequest());
    }

    @DeleteMapping("/{id}")
    public void deleteCharity(@PathVariable Long id) {
        charityService.deleteCharity(id);
    }

}
