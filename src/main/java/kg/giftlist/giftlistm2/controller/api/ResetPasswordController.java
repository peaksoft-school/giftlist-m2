package kg.giftlist.giftlistm2.controller.api;

import kg.giftlist.giftlistm2.db.entity.ResetPasswordToken;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.service.ResetPasswordTokenService;
import kg.giftlist.giftlistm2.db.service.UserService;
import kg.giftlist.giftlistm2.exception.TokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/reset-password")
@RequiredArgsConstructor
@Slf4j
public class ResetPasswordController {
    private final UserService userService;
    private final ResetPasswordTokenService passwordResetTokenService;

    @GetMapping
    public ResetPasswordToken get(@RequestParam String token){
        ResetPasswordToken resetToken=passwordResetTokenService.findByToken(token);
        if (resetToken==null){
            System.out.println("token not found");
            throw new TokenException("token not found");
        } else if (resetToken.getExpirationTime().isBefore(LocalDateTime.now())) {
            System.out.println("token is expired");
            throw new TokenException("token is expired");
        }else {
            return resetToken;
        }

    }
    @PostMapping
    public User resetPassword(@RequestParam String token, @RequestParam String password, @RequestParam String confirmPassword){
        ResetPasswordToken resetToken=passwordResetTokenService.findByToken(token);
        User user=resetToken.getUser();
        if (password.equals(confirmPassword)) {
            user.setPassword(password);
        }else {
            log.error("passwords do not match");
        }
        userService.updatePassword(user);
        return user;
    }
}
