package kg.giftlist.giftlistm2.controller.api;

import com.google.firebase.auth.FirebaseAuthException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.giftlist.giftlistm2.controller.payload.AuthRequest;
import kg.giftlist.giftlistm2.controller.payload.AuthResponse;
import kg.giftlist.giftlistm2.controller.payload.SignupRequest;
import kg.giftlist.giftlistm2.db.service.ResetPasswordService;
import kg.giftlist.giftlistm2.db.service.UserService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("test")
    public String test(Principal principal) {
        return principal.getName();
    }

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

    @PostMapping("google")
    public AuthResponse googleSignIn(@RequestParam String token) throws FirebaseAuthException {
        return userService.authWithGoogle(token);
    }

    @PostMapping("forgot-password")
    @Operation(summary = "process forgot password", description = "User The user can get a link to gmail to reset the password")
    public String processForgotPassword(@RequestParam("email") String email, HttpServletRequest request) {
        return resetPasswordService.processForgotPassword(email, request);
    }

    @PostMapping("reset-password")
    @Operation(summary = "process reset password", description = "The user can update password using token")
    public AuthResponse resetPassword(@RequestParam String token, @RequestParam String password, @RequestParam String confirmPassword) {
        return resetPasswordService.save(token, password, confirmPassword);
    }

}
