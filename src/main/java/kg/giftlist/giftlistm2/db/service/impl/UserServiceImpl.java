package kg.giftlist.giftlistm2.db.service.impl;

import kg.giftlist.giftlistm2.config.jwt.JwtTokenUtil;
import kg.giftlist.giftlistm2.controller.payload.AuthRequest;
import kg.giftlist.giftlistm2.controller.payload.AuthResponse;
import kg.giftlist.giftlistm2.controller.payload.SignupRequest;
import kg.giftlist.giftlistm2.controller.payload.SignupResponse;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.repository.UserRepository;
import kg.giftlist.giftlistm2.db.service.UserService;
import kg.giftlist.giftlistm2.enums.Role;
import kg.giftlist.giftlistm2.exception.EmptyLoginException;
import kg.giftlist.giftlistm2.exception.IncorrectLoginException;
import kg.giftlist.giftlistm2.mapper.LoginMapper;
import kg.giftlist.giftlistm2.mapper.UserSignUpMapper;
import kg.giftlist.giftlistm2.validation.ValidationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserSignUpMapper signUpMapper;
    private final AuthenticationManager authenticationManager;
    private final LoginMapper loginMapper;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public SignupResponse register(SignupRequest signupRequest) {
        User user = signUpMapper.toUser(signupRequest);
        if (signupRequest.getPassword().equals(signupRequest.getConfirmPassword())) {
            user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        } else {
            log.error("password not match");
        }
        user.setRole(Role.USER);
        userRepository.save(user);
        return signUpMapper.signupResponse(user);
    }

    @Override
    public AuthResponse login(AuthRequest loginRequest) {
        User user;
        User user2 = userRepository.findByEmail(loginRequest.getEmail());
        if (loginRequest.getEmail().isEmpty()) {
            throw new EmptyLoginException(ValidationType.EMPTY_EMAIL);
        }
        if (loginRequest.getPassword().isEmpty()) {
            throw new EmptyLoginException(ValidationType.EMPTY_PASSWORD);
        }
        if (user2 != null && passwordEncoder.matches(loginRequest.getPassword(), user2.getPassword())) {
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            user = userRepository.findByEmail(token.getName());
            String token1 = (jwtTokenUtil.generateToken(user));
            return loginMapper.loginView(token1, ValidationType.SUCCESSFUL, user);
        } else {
            throw new IncorrectLoginException(ValidationType.LOGIN_FAILED);
        }
    }

}






