package kg.giftlist.giftlistm2.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.giftlist.giftlistm2.controller.payload.Response;
import kg.giftlist.giftlistm2.db.service.FriendsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/friends")
@CrossOrigin
@RequiredArgsConstructor
@Tag(name = "Auth API", description = "Any user can do add user")
public class FriendsController {
    private final FriendsService friendsService;
    @GetMapping()
    public Response getAllFriends(){
        return friendsService.getAllFriends();
    }
    @PostMapping("request/{id}")
    @Operation(summary = "Request to friend ", description = "we can request to friends")
    public Response requestToFriend(@PathVariable Long id) {
        return friendsService.requestToFriend(id);
    }
    @PostMapping("accept/{id}")
    public Response acceptedToFriend(@PathVariable Long id){
        return friendsService.acceptToFriend(id);
    }
    @PostMapping("refuse/{id}")
    public Response refuseRequestToFriend(@PathVariable Long id){
        return friendsService.refuseRequestToFriend(id);
    }
    @DeleteMapping("delete/{friendId}")
    public void deleteFriend(@PathVariable Long friendId){
        friendsService.deleteFriend(friendId);
    }

}