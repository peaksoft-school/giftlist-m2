package kg.giftlist.giftlistm2.controller.api;

import kg.giftlist.giftlistm2.db.entity.ResetPasswordToken;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.service.ResetPasswordTokenServiceImpl;
import kg.giftlist.giftlistm2.db.service.UserServiceImpl;
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
    private final UserServiceImpl userServiceImpl;
    private final ResetPasswordTokenServiceImpl passwordResetTokenServiceImpl;

    @GetMapping
    public ResetPasswordToken get(@RequestParam String token){
        ResetPasswordToken resetToken=passwordResetTokenServiceImpl.findByToken(token);
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
        ResetPasswordToken resetToken=passwordResetTokenServiceImpl.findByToken(token);
        User user=resetToken.getUser();
        if (password.equals(confirmPassword)) {
            user.setPassword(password);
        }else {
            log.error("passwords do not match");
        }
        userServiceImpl.updatePassword(user);
        return user;
    }

}
