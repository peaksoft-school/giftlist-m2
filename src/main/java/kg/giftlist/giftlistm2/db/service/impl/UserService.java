package kg.giftlist.giftlistm2.db.service.impl;

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

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final LoginMapper loginMapper;
    private final JwtTokenUtil jwtTokenUtil;
//    public SignupResponse register(SignupRequest signupRequest) {
//        User user =signUpMapper.toUser(signupRequest);
//        if (signupRequest.getPassword()==null){
//            user.setPassword(passwordEncoder.encode(signupRequest.getFirstName()));
//        }
//        else if (signupRequest.getPassword().equals(signupRequest.getConfirmPassword())){
//            user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
//        }else {
//            log.error("password not match");
//        }
//        user.setRole(Role.USER);
//        userRepository.save(user);
//        return signUpMapper.signupResponse(user);
//    }

    public AuthResponse register(SignupRequest signupRequest) {
        User user = mapToRegisterRequest(signupRequest);
        if (signupRequest.getFirstName().isEmpty() || signupRequest.getLastName().isEmpty()) {
            throw new EmptyLoginException(ValidationType.EMPTY_FIELD);
        }
        if (signupRequest.getEmail().isEmpty()) {
            throw new EmptyLoginException(ValidationType.EMPTY_EMAIL);
        }
        if (signupRequest.getPassword() == null) {
            user.setPassword(passwordEncoder.encode(signupRequest.getFirstName()));
        }
        else if (signupRequest.getPassword().equals(signupRequest.getConfirmPassword())) {
            user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        } else {
            log.error("password not match");

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
    public void updatePassword(User user) {
        User user1 = userRepository.findById(user.getId()).get();
        user1.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user1);
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
            throw new IncorrectLoginException(ValidationType.LOGIN_FAILED);
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
//    public AuthResponse loginWithGoogle(Principal principal){
//        JSONObject jsonObject = new JSONObject(principal);
//        AuthRequest request = new AuthRequest();
//        request.setEmail(jsonObject.getJSONObject("principal").getJSONObject("claims").getString("email"));
//       return login(request);
//
//    }

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

