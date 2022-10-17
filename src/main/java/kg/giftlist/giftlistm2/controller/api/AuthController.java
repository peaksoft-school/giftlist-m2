package kg.giftlist.giftlistm2.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.giftlist.giftlistm2.controller.payload.AuthRequest;
import kg.giftlist.giftlistm2.controller.payload.AuthResponse;
import kg.giftlist.giftlistm2.controller.payload.SignupRequest;
import kg.giftlist.giftlistm2.controller.payload.SignupResponse;
import kg.giftlist.giftlistm2.db.service.UserService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/public")
@CrossOrigin
@RequiredArgsConstructor
@Tag(name = "Auth API", description = "Any user can do registration and login")
public class AuthController {

    private final UserService userService;

    @PostMapping("signin")
    @Operation(summary = "Login", description = "Only registered users can login")
    public AuthResponse login(@RequestBody AuthRequest loginRequest) {
        return userService.login(loginRequest);
    }

    @PostMapping("signup")
    @Operation(summary = "Registration", description = "Any user can register")
    public SignupResponse register(@RequestBody SignupRequest request) {
        return userService.register(request);
    }

    @GetMapping("oauth2")
    @Operation(summary = "register with google", description = "user use email can register")
    public SignupResponse signUpGoogle(Principal principal) {
        JSONObject jsonObject = new JSONObject(principal);
        SignupRequest request = new SignupRequest();
        request.setFirstName(jsonObject.getJSONObject("principal").getString("givenName"));
        request.setLastName(jsonObject.getJSONObject("principal").getString("familyName"));
        request.setEmail(jsonObject.getJSONObject("principal").getJSONObject("claims").getString("email"));
        return userService.register(request);
    }

}
