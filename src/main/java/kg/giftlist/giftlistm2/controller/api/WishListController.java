package kg.giftlist.giftlistm2.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.giftlist.giftlistm2.controller.payload.WishListRequest;
import kg.giftlist.giftlistm2.controller.payload.WishListResponse;
import kg.giftlist.giftlistm2.db.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/wishlists")
@CrossOrigin
@Tag(name = "WishList API", description = "User can get wish list by id, get all wish lists, create, update, book, unbook or delete wish list, " +
        "admin can block or unblock a wish list, get a wish list by id or get all wish lists")
@SecurityRequirement(name = "Authorization")
@RequiredArgsConstructor
public class WishListController {

    private final WishListService wishListService;

    @Operation(summary = "Get wish list", description = "Getting a wish list by id")
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('USER')")
    public WishListResponse getWishListById(@PathVariable Long id) {
        return wishListService.getWishListById(id);
    }

    @Operation(summary = "Get all wish lists", description = "Getting all wish lists")
    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    public List<WishListResponse> getAllWishLists() {
        return wishListService.getAllWishLists();
    }

    @Operation(summary = "Add wish list", description = "Adding a wish list by id")
    @PostMapping("add/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public WishListResponse addWishList(@PathVariable Long id) {
        return wishListService.addWishList(id);
    }

    @Operation(summary = "Creat new wish list", description = "Creating wish list")
    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    public WishListResponse creatWishList(@RequestBody WishListRequest request) {
        return wishListService.create(request);
    }

    @Operation(summary = "Update a wish list", description = "Updating a wish list by id")
    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('USER')")
    public WishListResponse updateWishList(@PathVariable Long id,
                                           @RequestBody WishListRequest request) {
        return wishListService.update(id, request);
    }

    @Operation(summary = "Wish list booking", description = "Booking a wish list")
    @PostMapping("book/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public String booking(@PathVariable Long id) {
        return wishListService.book(id);
    }

    @Operation(summary = "Wish list unbooking", description = "Unbooking a wish list(deleting a book by id which contains wish list)")
    @DeleteMapping("unbook/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public String unBook(@PathVariable Long id) {
        return wishListService.unBook(id);
    }

    @Operation(summary = "Delete wish list", description = "Deleting a wish list by id")
    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('USER')")
    public String deleteWishList(@PathVariable Long id) {
        return wishListService.delete(id);
    }

    @Operation(summary = "Admin gets a wish list", description = "Admin can get a wish list by id")
    @GetMapping("admin/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public WishListResponse getWishlistByAdmin(@PathVariable Long id) {
        return wishListService.getWishlistByAdmin(id);
    }

    @Operation(summary = "Admin gets all wish lists", description = "Admin can get all wish lists")
    @GetMapping("admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<WishListResponse> getAllWishlistsByAdmin() {
        return wishListService.getAllWishlistsByAdmin();
    }

    @Operation(summary = "Block wish list", description = "Admin can block a wish list by id")
    @PostMapping("block/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String blockWishlist(@PathVariable Long id) {
        return wishListService.blockWishlistByAdmin(id);
    }

    @Operation(summary = "Unblock wish list", description = "Admin can unblock a wish list by id")
    @PostMapping("unblock/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String unBlockWishlist(@PathVariable Long id) {
        return wishListService.unBlockWishlistByAdmin(id);
    }

}
