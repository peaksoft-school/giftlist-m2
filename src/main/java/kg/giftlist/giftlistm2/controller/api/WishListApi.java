package kg.giftlist.giftlistm2.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.giftlist.giftlistm2.controller.payload.request.WishListRequest;
import kg.giftlist.giftlistm2.controller.payload.response.WishListResponse;
import kg.giftlist.giftlistm2.db.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/wishlists")
@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin(origins = "*", maxAge = 3600)
@SecurityRequirement(name = "Authorization")
@Tag(name = "WishList API", description = "Wishlist endpoints")
public class WishListApi {

    private final WishListService wishListService;

    @Operation(summary = "Get wish list", description = "Getting a wish list by id")
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("{id}")
    public WishListResponse getWishListById(@PathVariable Long id) {
        return wishListService.getWishListById(id);
    }

    @Operation(summary = "Get all wish lists", description = "Getting all wish lists")
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping
    public List<WishListResponse> getAllWishLists() {
        return wishListService.getAllWishLists();
    }

    @Operation(summary = "Add wish list", description = "Adding a wish list by id")
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("add/{id}")
    public WishListResponse addWishList(@PathVariable Long id) {
        return wishListService.addWishList(id);
    }

    @Operation(summary = "Creat new wish list", description = "Creating wish list")
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping
    public WishListResponse creatWishList(@RequestBody WishListRequest request) {
        return wishListService.create(request);
    }

    @Operation(summary = "Update a wish list", description = "Updating a wish list by id")
    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("{id}")
    public WishListResponse updateWishList(@PathVariable Long id, @RequestBody WishListRequest request) {
        return wishListService.update(id, request);
    }

    @Operation(summary = "Wish list booking", description = "Booking a wish list")
    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("book/{id}")
    public String booking(@PathVariable Long id) {
        return wishListService.book(id);
    }

    @Operation(summary = "Wish list unbook", description = "Unbooking a wish list(deleting a book by id which contains wish list)")
    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("unbook/{id}")
    public String unBook(@PathVariable Long id) {
        return wishListService.unBook(id);
    }

    @Operation(summary = "Delete wish list", description = "Deleting a wish list by id")
    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("{id}")
    public String deleteWishList(@PathVariable Long id) {
        return wishListService.delete(id);
    }

    @Operation(summary = "Admin gets a wish list", description = "Admin can get a wish list by id")
    @GetMapping("admin/{id}")
    public WishListResponse getWishlistByAdmin(@PathVariable Long id) {
        return wishListService.getWishlistByAdmin(id);
    }

    @Operation(summary = "Admin gets all wish lists", description = "Admin can get all wish lists")
    @GetMapping("admin")
    public List<WishListResponse> getAllWishlistsByAdmin() {
        return wishListService.getAllWishlistsByAdmin();
    }

    @Operation(summary = "Block wish list", description = "Admin can block a wish list by id")
    @PostMapping("block/{id}")
    public String blockWishlist(@PathVariable Long id) {
        return wishListService.blockWishlistByAdmin(id);
    }

    @Operation(summary = "Unblock wish list", description = "Admin can unblock a wish list by id")
    @PostMapping("unblock/{id}")
    public String unBlockWishlist(@PathVariable Long id) {
        return wishListService.unBlockWishlistByAdmin(id);
    }

}
