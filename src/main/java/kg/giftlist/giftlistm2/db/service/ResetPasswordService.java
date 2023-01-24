package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.config.jwt.JwtTokenUtil;
import kg.giftlist.giftlistm2.controller.payload.request.Mail;
import kg.giftlist.giftlistm2.controller.payload.response.AuthResponse;
import kg.giftlist.giftlistm2.db.entity.ResetPasswordToken;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.repository.ResetPasswordTokenRepository;
import kg.giftlist.giftlistm2.db.repository.UserRepository;
import kg.giftlist.giftlistm2.exception.IncorrectLoginException;
import kg.giftlist.giftlistm2.exception.UserNotFoundException;
import kg.giftlist.giftlistm2.mapper.UserInfoViewMapper;
import kg.giftlist.giftlistm2.validation.ValidationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResetPasswordService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ResetPasswordTokenRepository resetPasswordTokenRepository;
    private final JwtTokenUtil jwtTokenUtil;
    public final EmailService emailService;
    public final UserInfoViewMapper userInfoViewMapper;

    public AuthResponse save(String token, String password, String confirmPassword) {
        ResetPasswordToken resetToken = resetPasswordTokenRepository.findByToken(token);
        User user = resetToken.getUser();
        if (password.equals(confirmPassword)) {
            user.setPassword(password);
        } else {
            log.error("Password don't match");
            throw new IncorrectLoginException("Password don't match");
        }
        updatePassword(user);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setId(user.getId());
        authResponse.setFirstName(user.getFirstName());
        authResponse.setLastName(user.getLastName());
        authResponse.setEmail(user.getEmail());
        authResponse.setJwtToken(token);
        authResponse.setMessage(ValidationType.SUCCESSFUL);
        authResponse.setAuthorities(String.valueOf(user.getRole()));
        log.info("New password successfully saved");
        return authResponse;
    }

    public void updatePassword(User user) {
        User user1 = userRepository.findById(user.getId()).orElseThrow(NoSuchElementException::new);
        user1.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user1);
    }

    public String processForgotPassword(String email, HttpServletRequest request) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User with email" + email + "not found");
        }
        ResetPasswordToken resetToken = new ResetPasswordToken();
        resetToken.setUser(user);
        resetToken.setToken(jwtTokenUtil.generateToken(user));
        resetToken.setExpirationTime(LocalDateTime.now().plusMinutes(30));
        Mail mail = new Mail();
        mail.setFrom("temuchi500@gmail.com");
        mail.setTo(user.getEmail());
        mail.setSubject("Password reset request");
        Map<String, Object> mailModel = new HashMap<>();
        mailModel.put("token", resetToken);
        mailModel.put("user", user);
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/api/public/";
        mailModel.put("resetUrl", url + "/reset-password?token=" + resetToken.getToken());
        System.out.println(url + " " + resetToken.getToken());
        String URL = url + "reset-password?token=" + resetToken.getToken();
        mail.setModel(mailModel);
        resetPasswordTokenRepository.save(resetToken);
        emailService.sendEmail(mail, URL);
        log.info("Successfully send link to change password");
        return ValidationType.SUCCESSFUL;
    }

}

