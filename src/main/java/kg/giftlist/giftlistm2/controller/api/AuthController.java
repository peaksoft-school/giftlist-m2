package kg.giftlist.giftlistm2.controller.api;

import kg.giftlist.giftlistm2.controller.payload.SignupRequest;
import kg.giftlist.giftlistm2.controller.payload.SignupResponse;
import kg.giftlist.giftlistm2.db.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.giftlist.giftlistm2.controller.payload.LoginRequest;
import kg.giftlist.giftlistm2.controller.payload.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/public")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Auth API", description = "Any user can do authentication")
public class AuthController {

    private final UserService userService;

    @Operation(summary = "Login", description = "Only registered users can login")
    @PostMapping("signin")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    @Operation(summary = "Registration", description = "Any user can register")
    @PostMapping("signup")
    public SignupResponse register(@RequestBody SignupRequest request) {
        return userService.register(request);
    }

}
