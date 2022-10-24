package kg.giftlist.giftlistm2.db.service.impl;

import kg.giftlist.giftlistm2.config.jwt.JwtTokenUtil;
import kg.giftlist.giftlistm2.controller.payload.AuthResponse;
import kg.giftlist.giftlistm2.controller.payload.SignupRequest;
import kg.giftlist.giftlistm2.controller.payload.SignupResponse;
import kg.giftlist.giftlistm2.controller.payload.UserChangePasswordRequest;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.repository.UserRepository;
import kg.giftlist.giftlistm2.db.service.UserService;
import kg.giftlist.giftlistm2.enums.Role;
import kg.giftlist.giftlistm2.mapper.UserSignUpMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserSignUpMapper signUpMapper;
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

    @Transactional
    public AuthResponse changeUserPassword(UserChangePasswordRequest userChangePasswordRequest) {
        User user = getAuthenticatedUser();
        if (!passwordEncoder.matches(userChangePasswordRequest.getCurrentPassword(), user.getPassword())) {
            log.error("invalid password");
            throw new NotFoundException(
                    "invalid password");
        } else {
//            editMapper.changePassword(user, userChangePasswordRequest);
            user.setPassword(passwordEncoder.encode(userChangePasswordRequest.getNewPassword()));
            log.info("Password successfully changed");
            return new AuthResponse(
                    user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getImage(),
                    user.getEmail(),
                    jwtTokenUtil.generateToken(user),
                    user.getRole()
            );
        }
    }
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login).orElseThrow(() -> new UsernameNotFoundException("Username not found "));
    }
}

