package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.config.jwt.JwtTokenUtil;
import kg.giftlist.giftlistm2.controller.payload.AuthRequest;
import kg.giftlist.giftlistm2.controller.payload.AuthResponse;
import kg.giftlist.giftlistm2.controller.payload.SignupRequest;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.repository.UserRepository;
import kg.giftlist.giftlistm2.enums.Role;
import kg.giftlist.giftlistm2.exception.EmptyLoginException;
import kg.giftlist.giftlistm2.exception.IncorrectLoginException;
import kg.giftlist.giftlistm2.mapper.LoginMapper;
import kg.giftlist.giftlistm2.validation.ValidationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final LoginMapper loginMapper;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthResponse register(SignupRequest signupRequest) {
        User user2 = new User();
        userRepository.findByEmail(user2.getEmail());
        Optional<User> search = Optional.ofNullable(userRepository.findByEmail(signupRequest.getEmail()));
        boolean notRegistered = search.isEmpty();
        User user = mapToRegisterRequest(signupRequest);
        if (signupRequest.getFirstName().isEmpty() || signupRequest.getLastName().isEmpty()) {
            throw new EmptyLoginException(ValidationType.EMPTY_FIELD);
        }
        if (!notRegistered) {
            throw new IncorrectLoginException(ValidationType.EXIST_EMAIL);
        }
        if (signupRequest.getEmail().isEmpty()) {
            throw new EmptyLoginException(ValidationType.EMPTY_EMAIL);
        }
        if (signupRequest.getPassword() == null) {
            user.setPassword(passwordEncoder.encode(signupRequest.getFirstName()));
        } else if (signupRequest.getPassword().equals(signupRequest.getConfirmPassword())) {
            user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        } else {
            log.error("password not match");
            throw new IncorrectLoginException("Passwords do not match");
        }
        user.setRole(Role.USER);
        userRepository.save(user);
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(signupRequest.getEmail(), signupRequest.getPassword());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signupRequest.getEmail(), signupRequest.getPassword()));
        user = userRepository.findByEmail(token.getName());
        String token1 = (jwtTokenUtil.generateToken(user));
        return loginMapper.loginView(token1, ValidationType.SUCCESSFUL, user);
    }

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
            throw new IncorrectLoginException(ValidationType.LOGIN_FAILED + " or " + ValidationType.NOT_REGISTERED);
        }
    }

    public AuthResponse signupWithGoogle(Principal principal) {
        JSONObject jsonObject = new JSONObject(principal);
        SignupRequest request = new SignupRequest();
        request.setFirstName(jsonObject.getJSONObject("principal").getString("givenName"));
        request.setLastName(jsonObject.getJSONObject("principal").getString("familyName"));
        request.setEmail(jsonObject.getJSONObject("principal").getJSONObject("claims").getString("email"));
        return register(request);
    }

    public User mapToRegisterRequest(SignupRequest signupRequest) {
        if (signupRequest == null) {
            return null;
        }
        User user = new User();
        user.setFirstName(signupRequest.getFirstName());
        user.setLastName(signupRequest.getLastName());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(signupRequest.getConfirmPassword());
        return user;
    }

}

