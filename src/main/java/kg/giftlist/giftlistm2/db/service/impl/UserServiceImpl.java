package kg.giftlist.giftlistm2.db.service.impl;

import kg.giftlist.giftlistm2.controller.payload.AuthResponse;
import kg.giftlist.giftlistm2.controller.payload.SignupRequest;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.repository.UserRepository;
import kg.giftlist.giftlistm2.db.service.UserService;
import kg.giftlist.giftlistm2.enums.Role;
import kg.giftlist.giftlistm2.mapper.UserSignUpMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserSignUpMapper signUpMapper;

    @Override
    public AuthResponse register(SignupRequest signupRequest) {
         User user =signUpMapper.toUser(signupRequest);
        if (signupRequest.getPassword().equals(signupRequest.getConfirmPassword())){
            user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        }else {
            log.error("password not match");
        }
        user.setRole(Role.USER);
         userRepository.save(user);
        return (signUpMapper.authResponse(user));
    }
    }

