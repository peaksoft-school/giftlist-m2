package kg.giftlist.giftlistm2.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.giftlist.giftlistm2.mapper.LoginMapper;
import kg.giftlist.giftlistm2.controller.payload.LoginRequest;
import kg.giftlist.giftlistm2.controller.payload.LoginResponse;
import kg.giftlist.giftlistm2.validation.ValidationType;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.repository.UserRepository;
import kg.giftlist.giftlistm2.config.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository repository;
    private final JwtTokenUtil jwtTokenUtil;
    private final LoginMapper loginMapper;
    private final AuthenticationManager authenticationManager;

    @Operation(summary = "Login", description = "User can do login")
    @PostMapping("login")
    public ResponseEntity<LoginResponse> getLogin(@RequestBody LoginRequest request) {
        try {
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            User user = repository.findByEmail(token.getName()).get();
            return ResponseEntity.ok().body(loginMapper.loginView(jwtTokenUtil.generateToken(user), ValidationType.SUCCESSFUL, user));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(loginMapper.loginView("", ValidationType.LOGIN_FAILED, null));
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity processForgotPassword(@RequestParam("email") String email, HttpServletRequest request) {
        User user = userServiceImpl.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User with email" + email + "not found");
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
        mailModel.put("resetUrl",url + "/reset-password?token=" + resetToken.getToken());
        System.out.println(url + " " + resetToken.getToken());
        String URL = url + "reset-password?token=" + resetToken.getToken();
        mail.setModel(mailModel);

        emailServiceImpl.sendEmail(mail,URL);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping
    public ResetPasswordToken get(@RequestParam String token){
        ResetPasswordToken resetToken = passwordResetTokenServiceImpl.findByToken(token);
        if(resetToken == null){
            System.out.println("token not found");
            throw  new TokenException("token no found");
        }else if (resetToken.getExpirationTime().isBefore(LocalDateTime.now())){
            System.out.println("token is expired");
            throw new TokenException("token is expired");
        }else {
            return resetToken;
        }
    }

    @PostMapping("/reset-password")
    public  User resetPassword(@RequestParam String token,@RequestParam String password,@RequestParam String confirmPassword){
        ResetPasswordToken resetToken = passwordResetTokenServiceImpl.findByToken(token);
        User user = resetToken.getUser();
        if(password.equals(confirmPassword)) {
            user.setPassword(password);
        }else {
            log.error("passwords do not match");
        }
        userServiceImpl.updatePassword(user);
        return user;
    }

}
