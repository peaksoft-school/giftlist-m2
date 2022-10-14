package kg.giftlist.giftlistm2.controller.api;

import io.swagger.v3.oas.annotations.Operation;
        import io.swagger.v3.oas.annotations.tags.Tag;
import kg.giftlist.giftlistm2.controller.payload.FriendsRequest;
import kg.giftlist.giftlistm2.controller.payload.FriendsResponse;
import kg.giftlist.giftlistm2.db.service.FriendsService;
import lombok.RequiredArgsConstructor;
        import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/friends")
@CrossOrigin
@RequiredArgsConstructor
@Tag(name = "Auth API",description = "Any user can do add user")
public class FriendsController {
    private final FriendsService friendsService;

    @PostMapping("/{id}")
    @Operation(summary = "add friends ", description = "we can add friends")
    public FriendsResponse requestToFriend(@PathVariable Long id) {
        return friendsService.requestToFriend(id);
    }

}