package kg.giftlist.giftlistm2.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.giftlist.giftlistm2.controller.payload.response.WishListResponse;
import kg.giftlist.giftlistm2.db.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/feed")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasAnyAuthority('USER')")
@Tag(name = "Feed API", description = "Users with role  \"User\" can see feed")
public class FeedApi {

    private final WishListService wishListService;

    @Operation(summary = "Get friend's wishes and all wishes", description = "User can see friend's wishes and all wishes")
    @GetMapping
    public List<WishListResponse> getAllWishes() {
        return wishListService.getWishesForFeed();
    }

    @Operation(summary = "Get wish list", description = "Getting a wish list by id")
    @GetMapping("{id}")
    public WishListResponse getWishListById(@PathVariable Long id) {
        return wishListService.getWishListById(id);
    }

}
