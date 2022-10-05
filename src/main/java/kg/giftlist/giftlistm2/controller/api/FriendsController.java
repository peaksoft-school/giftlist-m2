package kg.giftlist.giftlistm2.controller.api;

import io.swagger.v3.oas.annotations.Operation;
        import io.swagger.v3.oas.annotations.tags.Tag;
        import kg.giftlist.giftlistm2.controller.payload.FriendsResponse;
        import lombok.RequiredArgsConstructor;
        import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/friends")
@CrossOrigin
@RequiredArgsConstructor
@Tag(name = "Auth API",description = "Any user can do add user")
public class FriendsController {

    @PostMapping("/add")
    @Operation(summary = "add friends ", description = "we can add friends")
    public FriendsResponse addFriends(@RequestBody Long userId) {
        return addFriends(userId);
    }

}