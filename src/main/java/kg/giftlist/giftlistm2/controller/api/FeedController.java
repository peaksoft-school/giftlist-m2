package kg.giftlist.giftlistm2.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.giftlist.giftlistm2.controller.payload.WishListResponse;
import kg.giftlist.giftlistm2.db.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/feed")
@RequiredArgsConstructor
@CrossOrigin
@PreAuthorize("hasAnyAuthority('USER')")
@Tag(name = "Feed API", description = "Users with role  \"User\" can see feed")
public class FeedController {

    private final FeedService feedService;

    @Operation(summary = "Get friend's wishes and all wishes", description = "User can see friend's wishes and all wishes")
    @GetMapping
    public List<WishListResponse> getAllWishes() {
        return feedService.getWishesForFeed();
    }

}
