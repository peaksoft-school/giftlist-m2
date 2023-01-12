package kg.giftlist.giftlistm2.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.giftlist.giftlistm2.controller.payload.response.AuthResponse;
import kg.giftlist.giftlistm2.controller.payload.request.UserChangePasswordRequest;
import kg.giftlist.giftlistm2.controller.payload.request.UserInfoRequest;
import kg.giftlist.giftlistm2.controller.payload.response.UserInfoResponse;
import kg.giftlist.giftlistm2.db.service.UserInfoService;
import kg.giftlist.giftlistm2.db.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/profile")
@PreAuthorize("hasAnyAuthority('USER')")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "User API", description = "Users with role  \"User\" can view profile, change password, update profile")
public class UserProfileApi {

    private final UserInfoService userInfoService;
    private final UserService userService;

    @Operation(summary = "Update user profile information ", description = "User can update profile information")
    @PatchMapping("{id}")
    public UserInfoResponse updateUserProfile(@PathVariable Long id, @RequestBody UserInfoRequest userInfoRequest) {
        return userInfoService.update(id, userInfoRequest);
    }

    @Operation(summary = "Get user profile ", description = "Find by id user profile")
    @GetMapping("me")
    public UserInfoResponse getUserProfile() {
        return userInfoService.findById();
    }

    @Operation(summary = "Change password ", description = "User can change password")
    @PostMapping("password-change")
    public AuthResponse changePassword(@RequestBody UserChangePasswordRequest request) {
        return userService.changeUserPassword(request);
    }

    @Operation(summary = "Logout", description = "User can logout")
    @GetMapping("logout")
    public String logout(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            request.getSession().invalidate();
        }
        return "redirect:/";
    }

}
