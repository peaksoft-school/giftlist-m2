package kg.giftlist.giftlistm2.controller.api;

import kg.giftlist.giftlistm2.config.jwt.JwtTokenUtil;
import kg.giftlist.giftlistm2.db.Mail;
import kg.giftlist.giftlistm2.db.entity.ResetPasswordToken;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.service.EmailServiceImpl;
import kg.giftlist.giftlistm2.db.service.ResetPasswordTokenServiceImpl;
import kg.giftlist.giftlistm2.db.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/forgot-password")
@RequiredArgsConstructor
public class ForgotPasswordController {

    private final UserServiceImpl userServiceImpl;
    private final ResetPasswordTokenServiceImpl passwordResetTokenServiceImpl;
    private final EmailServiceImpl emailServiceImpl;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping
    public ResponseEntity processForgotPassword(@RequestParam("email") String email,
                                                HttpServletRequest request) {
        User user = userServiceImpl.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User with email " + email + "not found");
        }
        ResetPasswordToken resetToken = new ResetPasswordToken();
        resetToken.setUser(user);
        resetToken.setToken(jwtTokenUtil.generateToken(user));
        resetToken.setExpirationTime(LocalDateTime.now().plusMinutes(30));
        resetToken = passwordResetTokenServiceImpl.save(resetToken);
        Mail mail = new Mail();
        mail.setFrom("arzimatovanurperi@gmail.com");
        mail.setTo(user.getEmail());
        mail.setSubject("Password reset request");
        Map<String, Object> mailModel = new HashMap<>();
        mailModel.put("token", resetToken);
        mailModel.put("user", user);
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        mailModel.put("resetUrl", url + "/reset-password?token=" + resetToken.getToken());
        System.out.println(url + " " + resetToken.getToken());
        String URL= url + "/reset-password?token=" + resetToken.getToken();
        mail.setModel(mailModel);

        emailServiceImpl.sendEmail(mail, URL);
        return new ResponseEntity(HttpStatus.OK);
    }

}