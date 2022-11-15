package kg.giftlist.giftlistm2.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.giftlist.giftlistm2.controller.payload.AdminPageUserGetAllResponse;
import kg.giftlist.giftlistm2.controller.payload.CommonUserProfileResponse;
import kg.giftlist.giftlistm2.controller.payload.SimpleResponse;
import kg.giftlist.giftlistm2.db.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("api/admin")
@PreAuthorize("hasAnyAuthority('ADMIN')")
@Tag(name = "Admin API", description = "User with role 'Admin'  can block, unblock and get users")
public class AdminController {

    private final AdminService adminService;

    @Operation(summary = "Get all users", description = "Get all users ")
    @GetMapping("users")
    public List<AdminPageUserGetAllResponse> getAllUsers() {
        return adminService.getAllUsers();
    }

    @Operation(summary = "Get user profile ", description = "Find by id user profile")
    @GetMapping("/user/{userId}")
    public CommonUserProfileResponse getUserProfile(@PathVariable Long userId) {
        return adminService.getCommonFriendProfile(userId);
    }

    @Operation(summary = "Block User", description = "block user by id")
    @PutMapping("/blockUser/{userId}")
    public SimpleResponse block(@PathVariable("userId") Long id) {
        return adminService.blockUser(id);
    }

    @Operation(summary = "UnBlock User", description = "UnBlock user by id")
    @PutMapping("/unblockUser/{userId}")
    public SimpleResponse unBlock(@PathVariable("userId") Long id) {
        return adminService.unBlockUser(id);
    }

}
