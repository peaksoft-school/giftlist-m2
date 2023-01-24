package kg.giftlist.giftlistm2.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.giftlist.giftlistm2.controller.payload.response.FriendProfileResponse;
import kg.giftlist.giftlistm2.controller.payload.response.FriendResponse;
import kg.giftlist.giftlistm2.db.service.FriendsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/friends")
@PreAuthorize("hasAuthority('USER')")
@CrossOrigin(origins = "*", maxAge = 3600)
@SecurityRequirement(name = "Authorization")
@Tag(name = "Friend API", description = "The user can send a request, accept a request, unfriend, list all friends and all friend requests, and can see a friend's profile")
public class FriendApi {

    private final FriendsService friendsService;

    @Operation(summary = "Get friend ", description = "User can see friend's profile")
    @GetMapping("{friendId}")
    public FriendProfileResponse getFriendProfile(@PathVariable Long friendId) {
        return friendsService.getFriend(friendId);
    }

    @Operation(summary = "Get friends ", description = "Get all friends")
    @GetMapping()
    public List<FriendResponse> getAllFriends() {
        return friendsService.getAllFriends();
    }

    @Operation(summary = "Get friend requests ", description = "Get all friends requests")
    @GetMapping("request")
    public List<FriendResponse> getAllFriendsRequests() {
        return friendsService.getAllRequestToFriend();
    }

    @PostMapping("request/{id}")
    @Operation(summary = "Request to friend ", description = "User can send friend request")
    public FriendResponse requestToFriend(@PathVariable Long id) {
        return friendsService.requestToFriend(id);
    }

    @Operation(summary = "Accept as a friend ", description = "The user will be able to accept the user's friend request and add as a friend")
    @PostMapping("accept/{id}")
    public FriendResponse acceptedToFriend(@PathVariable Long id) {
        return friendsService.acceptToFriend(id);
    }

    @Operation(summary = "Reject request ", description = "User can decline friend request")
    @DeleteMapping("reject/{id}")
    public String declineFriendRequest(@PathVariable Long id) {
        return friendsService.declineFriendRequest(id);
    }

    @Operation(summary = "Delete friend ", description = "User can delete user from friends")
    @DeleteMapping("{friendId}")
    public String deleteFriend(@PathVariable Long friendId) {
        return friendsService.deleteFriend(friendId);
    }

}