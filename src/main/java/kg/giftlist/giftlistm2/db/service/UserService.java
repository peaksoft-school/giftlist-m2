package kg.giftlist.giftlistm2.db.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import kg.giftlist.giftlistm2.config.jwt.JwtTokenUtil;
import kg.giftlist.giftlistm2.controller.payload.request.AuthRequest;
import kg.giftlist.giftlistm2.controller.payload.request.SignupRequest;
import kg.giftlist.giftlistm2.controller.payload.request.UserChangePasswordRequest;
import kg.giftlist.giftlistm2.controller.payload.response.AuthResponse;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.repository.UserRepository;
import kg.giftlist.giftlistm2.enums.Role;
import kg.giftlist.giftlistm2.exception.EmptyLoginException;
import kg.giftlist.giftlistm2.exception.IncorrectLoginException;
import kg.giftlist.giftlistm2.exception.UserExistException;
import kg.giftlist.giftlistm2.mapper.LoginMapper;
import kg.giftlist.giftlistm2.validation.ValidationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    @Value("classpath:serviceAccountKey.json")
    Resource serviceAccount;

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
            log.error(ValidationType.EMPTY_FIELD);
            throw new EmptyLoginException(ValidationType.EMPTY_FIELD);
        }
        if (!notRegistered) {
            log.error(ValidationType.EXIST_EMAIL);
            throw new IncorrectLoginException(ValidationType.EXIST_EMAIL);
        }
        if (signupRequest.getEmail().isEmpty()) {
            log.error(ValidationType.EMPTY_EMAIL);
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
        log.info("Registration was successfully");
        return loginMapper.loginView(token1, ValidationType.SUCCESSFUL, user);
    }

    public AuthResponse login(AuthRequest loginRequest) {
        User user;
        User existUser = userRepository.findByEmail(loginRequest.getEmail());
        if (loginRequest.getEmail().isEmpty()) {
            log.error(ValidationType.EMPTY_EMAIL);
            throw new EmptyLoginException(ValidationType.EMPTY_EMAIL);
        }
        if (loginRequest.getPassword().isEmpty()) {
            log.error(ValidationType.EMPTY_PASSWORD);
            throw new EmptyLoginException(ValidationType.EMPTY_PASSWORD);
        }
        if (existUser != null && passwordEncoder.matches(loginRequest.getPassword(), existUser.getPassword())) {
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            user = userRepository.findByEmail(token.getName());
            String token1 = (jwtTokenUtil.generateToken(user));
            log.info("Login was successful");
            return loginMapper.loginView(token1, ValidationType.SUCCESSFUL, user);
        } else {
            log.error(ValidationType.LOGIN_FAILED + " or " + ValidationType.NOT_REGISTERED);
            throw new IncorrectLoginException(ValidationType.LOGIN_FAILED + " or " + ValidationType.NOT_REGISTERED);
        }
    }

    public AuthResponse authWithGoogle(String token) throws FirebaseAuthException {
        FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(token);
        String uid = firebaseToken.getUid();
        if (!userRepository.getExistingEmail(firebaseToken.getEmail())) {
            User user = new User();
            user.setFirstName(firebaseToken.getName());
            String password = passwordEncoder.encode(firebaseToken.getEmail());
            user.setPassword(password);
            user.setEmail(firebaseToken.getEmail());
            user.setRole(Role.USER);
            userRepository.save(user);
            String customToken = FirebaseAuth.getInstance().createCustomToken(uid);
            UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);
            System.out.println(userRecord.getDisplayName());
            return loginMapper.loginView(customToken, ValidationType.SUCCESSFUL, user);
        } else {
            throw new UserExistException("User with email " + firebaseToken.getEmail() + " is existing");
        }
    }

    @Bean
    FirebaseAuth firebaseAuth() throws IOException {
        var options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream()))
                .build();
        var firebaseApp = FirebaseApp.initializeApp(options);
        return FirebaseAuth.getInstance(firebaseApp);
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
        user.setSubscribeToNewsletter(signupRequest.isSubscribeToNewsletter());
        return user;
    }

    @Transactional
    public AuthResponse changeUserPassword(UserChangePasswordRequest userChangePasswordRequest) {
        User user = getAuthenticatedUser();
        if (!passwordEncoder.matches(userChangePasswordRequest.getCurrentPassword(), user.getPassword())) {
            log.error("invalid password");
            throw new NotFoundException(
                    "invalid password");
        } else {
            user.setPassword(passwordEncoder.encode(userChangePasswordRequest.getNewPassword()));
            log.info("Password successfully changed");
            AuthResponse authResponse = new AuthResponse();
            authResponse.setId(user.getId());
            authResponse.setFirstName(user.getFirstName());
            authResponse.setLastName(user.getLastName());
            authResponse.setEmail(user.getEmail());
            authResponse.setJwtToken(jwtTokenUtil.generateToken(user));
            log.info("Password successfully changed");
            return authResponse;
        }

    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        log.info("User: " + authentication.getName());
        return userRepository.findByEmail(login);
    }

}

