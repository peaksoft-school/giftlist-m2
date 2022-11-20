package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.config.jwt.JwtTokenUtil;
import kg.giftlist.giftlistm2.db.entity.ResetPasswordToken;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.repository.ResetPasswordTokenRepository;
import kg.giftlist.giftlistm2.db.repository.UserRepository;
import kg.giftlist.giftlistm2.exception.EmptyValueException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;



@Service
@RequiredArgsConstructor
@Slf4j
public class ResetPasswordService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ResetPasswordTokenRepository resetPasswordTokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    public final EmailService emailService;


    public User save(String token, String password, String confirmPassword) {
        ResetPasswordToken resetToken = resetPasswordTokenRepository.findByToken(token);
        User user = resetToken.getUser();
        if (password.equals(confirmPassword)) {
            user.setPassword(password);
        } else {
            throw new EmptyValueException("Password don't match");
        }
        updatePassword(user);
        return user;
    }
    public ResetPasswordToken save1(ResetPasswordToken resetPasswordToken){
        return resetPasswordToken;
    }

    public void updatePassword(User user) {
        User user1 = userRepository.findById(user.getId()).get();
        user1.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user1);
    }

    public void processForgotPassword(String email, HttpServletRequest request) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User with email" + email + "not found");
        }
        ResetPasswordToken resetToken = new ResetPasswordToken();
        resetToken.setUser(user);
        resetToken.setToken(jwtTokenUtil.generateToken(user));
        resetToken.setExpirationTime(LocalDateTime.now().plusMinutes(30));
        resetToken = save1(resetToken);
        Mail mail = new Mail();
        mail.setFrom("temuchi500@gmail.com");
        mail.setTo(user.getEmail());
        mail.setSubject("Password reset request");
        Map<String, Object> mailModel = new HashMap<>();
        mailModel.put("token", resetToken);
        mailModel.put("user", user);
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        mailModel.put("resetUrl",url + "/reset-password?token=" + resetToken.getToken());
        System.out.println(url + " " + resetToken.getToken());
        String URL = url + "reset-password?token=" + resetToken.getToken();
        mail.setModel(mailModel);

        emailService.sendEmail(mail,URL);
    }
    public ResetPasswordToken get(String token){
        ResetPasswordToken resetToken = resetPasswordTokenRepository.findByToken(token);
        if(resetToken == null){
            System.out.println("token not found");
            throw  new EmptyValueException("token no found");
        }else if (resetToken.getExpirationTime().isBefore(LocalDateTime.now())){
            System.out.println("token is expired");
            throw new EmptyValueException("token is expired");
        }else {
            return resetToken;
        }
    }




}

