package kg.giftlist.giftlistm2.controller.api;

import com.google.firebase.auth.FirebaseAuthException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.giftlist.giftlistm2.controller.payload.request.AuthRequest;
import kg.giftlist.giftlistm2.controller.payload.response.AuthResponse;
import kg.giftlist.giftlistm2.controller.payload.request.SignupRequest;
import kg.giftlist.giftlistm2.db.service.ResetPasswordService;
import kg.giftlist.giftlistm2.db.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/public")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Auth API", description = "Any user can do authentication")
public class AuthApi {

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

    @Operation(summary = "Auth with google", description = "Authentication with google")
    @PostMapping("google")
    public AuthResponse googleSignIn(@RequestParam String token) throws FirebaseAuthException {
        return userService.authWithGoogle(token);
    }

    @Operation(summary = "process forgot password", description = "User The user can get a link to gmail to reset the password")
    @PostMapping("forgot-password")
    public String processForgotPassword(@RequestParam("email") String email, HttpServletRequest request) {
        return resetPasswordService.processForgotPassword(email, request);
    }

    @Operation(summary = "process reset password", description = "The user can update password using token")
    @PostMapping("reset-password")
    public AuthResponse resetPassword(@RequestParam String token, @RequestParam String password, @RequestParam String confirmPassword) {
        return resetPasswordService.save(token, password, confirmPassword);
    }

}
