package kg.giftlist.giftlistm2.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.giftlist.giftlistm2.controller.payload.AuthRequest;
import kg.giftlist.giftlistm2.controller.payload.AuthResponse;
import kg.giftlist.giftlistm2.controller.payload.SignupRequest;
import kg.giftlist.giftlistm2.db.entity.ResetPasswordToken;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.service.ResetPasswordService;
import kg.giftlist.giftlistm2.db.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/public")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Auth API", description = "Any user can do authentication")
public class AuthController {

    private final UserService userService;
    private final ResetPasswordService resetPasswordService;

    @Operation(summary = "Login", description = "Only registered users can login")
    @PostMapping("signin")
    public AuthResponse login(@RequestBody AuthRequest loginRequest) {
        return userService.login(loginRequest);
    }

    @Operation(summary = "Registration", description = "Any user can register")
    @PostMapping("signup")
    public AuthResponse register(@RequestBody SignupRequest request) {
        return userService.register(request);
    }

    @Operation(summary = "Registration with google", description = "User can be registered with google")
    @GetMapping("oauth2")
    public AuthResponse signupGoogle(Principal principal) {
        return userService.signupWithGoogle(principal);
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "process forgot password", description = "User The user can get a link to gmail to reset the password")
    public void processForgotPassword(@RequestParam("email") String email, HttpServletRequest request) {
        resetPasswordService.processForgotPassword(email,request);
    }

    @GetMapping
    @Operation(summary = "process reset password",description = "The user can access to update the password")
    public ResetPasswordToken get(@RequestParam String token){
        return resetPasswordService.get(token);
    }

    @PostMapping("/reset-password")
    @Operation(summary = "process reset password",description = "The user can update password using token")
    public User resetPassword(@RequestParam String token, @RequestParam String password, @RequestParam String confirmPassword){
        return resetPasswordService.save(token,password,confirmPassword);
    }

    }
