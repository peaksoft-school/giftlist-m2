package kg.giftlist.giftlistm2.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.giftlist.giftlistm2.controller.payload.response.AdminPageUserGetAllResponse;
import kg.giftlist.giftlistm2.controller.payload.response.CommonUserProfileResponse;
import kg.giftlist.giftlistm2.controller.payload.response.SimpleResponse;
import kg.giftlist.giftlistm2.db.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/admin")
@PreAuthorize("hasAnyAuthority('ADMIN')")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Admin API", description = "User with role 'Admin'  can block, unblock and get users")
public class AdminApi {

    private final AdminService adminService;

    @Operation(summary = "Get all users", description = "Get all users ")
    @GetMapping("users")
    public List<AdminPageUserGetAllResponse> getAllUsers() {
        return adminService.getAllUsers();
    }

    @Operation(summary = "Get user profile ", description = "Find user profile by user id ")
    @GetMapping("user/{userId}")
    public CommonUserProfileResponse getUserProfile(@PathVariable Long userId) {
        return adminService.getCommonFriendProfile(userId);
    }

    @Operation(summary = "Block User", description = "Block user by id")
    @PutMapping("block-user/{userId}")
    public SimpleResponse block(@PathVariable("userId") Long id) {
        return adminService.blockUser(id);
    }

    @Operation(summary = "Unblock User", description = "Unblock user by id")
    @PutMapping("unblock-user/{userId}")
    public SimpleResponse unBlock(@PathVariable("userId") Long id) {
        return adminService.unBlockUser(id);
    }

}
