package kg.giftlist.giftlistm2.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.giftlist.giftlistm2.controller.payload.WishListRequest;
import kg.giftlist.giftlistm2.controller.payload.WishListResponse;
import kg.giftlist.giftlistm2.db.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/wishLists")
@CrossOrigin
@Tag(name = "WishList API", description = "User can get wish list by id, get all wish lists, create, update or delete wish list")
@SecurityRequirement(name = "Authorization")
@RequiredArgsConstructor
public class WishListController {

    private final WishListService wishListService;

    @Operation(summary = "Get wish list", description = "Getting a wish list by id")
    @GetMapping("{id}")
    public WishListResponse getWishListById(@PathVariable Long id) {
        return wishListService.getWishListById(id);
    }

    @Operation(summary = "Add wish list", description = "Adding a wish list by id")
    @PostMapping("add/{id}")
    public WishListResponse addWishList(@PathVariable Long id) {
        return wishListService.addWishList(id);
    }

    @Operation(summary = "Get all wish lists", description = "Getting all wish lists")
    @GetMapping
    public List<WishListResponse> getAllWishLists() {
        return wishListService.getAllWishLists();
    }

    @Operation(summary = "Add a wish list", description = "Adding a wish list")
    @PostMapping
    public WishListResponse addWishList(@RequestBody WishListRequest request) {
        return wishListService.create(request);
    }

    @Operation(summary = "Update a wish list", description = "Updating a wish list by id")
    @PutMapping("{id}")
    public WishListResponse updateWishList(@PathVariable Long id,
                                           @RequestBody WishListRequest request) {
        return wishListService.update(id, request);
    }

    @Operation(summary = "Delete wish list", description = "Deleting a wish list by id")
    @DeleteMapping("{id}")
    public String deleteWishList(@PathVariable Long id) {
        return wishListService.delete(id);
    }

    @Operation(summary = "Wish list booking", description = "Booking a wish list")
    @PostMapping("{id}")
    public String booking(@PathVariable Long id) {
        return wishListService.book(id);
    }

    @Operation(summary = "Wish list booking", description = "Booking a wish list")
    @DeleteMapping("wishList/{id}")
    public String unBook(@PathVariable Long id) {
        return wishListService.unBook(id);
    }

}
