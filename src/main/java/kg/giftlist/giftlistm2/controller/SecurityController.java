package kg.giftlist.giftlistm2.controller;

import kg.giftlist.giftlistm2.dto.LoginMapper;
import kg.giftlist.giftlistm2.dto.LoginRequest;
import kg.giftlist.giftlistm2.dto.LoginResponse;
import kg.giftlist.giftlistm2.dto.ValidationType;
import kg.giftlist.giftlistm2.entity.User;
import kg.giftlist.giftlistm2.repository.UserRepository;
import kg.giftlist.giftlistm2.security.jwt.JwtTokenUtil;
import kg.giftlist.giftlistm2.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping
@RequiredArgsConstructor
@EnableOAuth2Client
public class SecurityController {

    private final UserServiceImpl userService;
    private final UserRepository repository;
    private final JwtTokenUtil jwtTokenUtil;
    private final LoginMapper loginMapper;
    private final AuthenticationManager authenticationManager;
    
    private OAuth2ClientContext oAuth2ClientContext;

    @PostMapping("/login")
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
}
