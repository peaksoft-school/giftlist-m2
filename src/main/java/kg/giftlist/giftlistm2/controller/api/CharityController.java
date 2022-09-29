package kg.giftlist.giftlistm2.controller.api;

import kg.giftlist.giftlistm2.controller.payload.CharityRequest;
import kg.giftlist.giftlistm2.controller.payload.CharityResponse;
import kg.giftlist.giftlistm2.db.entity.Charity;
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

    @GetMapping("/{id}")
    public Charity getCharityById(@PathVariable Long id) {
        return charityService.getCharityById(id);
    }

    @GetMapping
    public List<Charity> getAllCharities() {
        return charityService.getAllCharities();
    }

    @PostMapping
    public CharityResponse addCharity(@RequestBody CharityRequest request) {
        return charityService.createCharity(request);
    }

    @PutMapping("/{id}")
    public CharityResponse updateCharity(@PathVariable Long id, @RequestBody CharityRequest request) {
        return charityService.updateCharity(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteCharity(@PathVariable Long id) {
        charityService.deleteCharity(id);
    }

}
