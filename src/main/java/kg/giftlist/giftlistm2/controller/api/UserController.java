package kg.giftlist.giftlistm2.controller.api;

import kg.giftlist.giftlistm2.controller.payload.UserRequest;
import kg.giftlist.giftlistm2.controller.payload.UserResponse;
import kg.giftlist.giftlistm2.db.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sign-up")
@RequiredArgsConstructor
public class UserController {
     private final UserService userService;

     @PostMapping
    public UserResponse register(@RequestBody UserRequest userRequest){
        return userService.register(userRequest);
    }
}
