package kg.giftlist.giftlistm2.db.service.impl;

import kg.giftlist.giftlistm2.controller.payload.SignupRequest;
import kg.giftlist.giftlistm2.controller.payload.SignupResponse;
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

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }

    @Override
    public SignupResponse register(SignupRequest signupRequest) {
         User user =signUpMapper.toUser(signupRequest);
         if (signupRequest.getPassword()==null){
             user.setPassword(passwordEncoder.encode(signupRequest.getFirstName()));
         }
       else if (signupRequest.getPassword().equals(signupRequest.getConfirmPassword())){
            user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        }else {
            log.error("password not match");
        }
        user.setRole(Role.USER);
         userRepository.save(user);
        return signUpMapper.signupResponse(user);
    }
    public void updatePassword(User user) {
        User user1 = userRepository.findById(user.getId()).get();
        user1.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user1);
    }
    }

