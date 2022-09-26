package kg.giftlist.giftlistm2.db.service.impl;

import kg.giftlist.giftlistm2.controller.payload.UserRequest;
import kg.giftlist.giftlistm2.controller.payload.UserResponse;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.repository.UserRepository;
import kg.giftlist.giftlistm2.db.service.UserService;
import kg.giftlist.giftlistm2.enums.Role;
import kg.giftlist.giftlistm2.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    @Override
    public UserResponse register(UserRequest userRequest) {
        User user=userMapper.toUser(userRequest);
        if (userRequest.getPassword().equals(userRequest.getConfirmPassword())){
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }else {
            log.error("password not match");
        }
        user.setRole(Role.USER);
        userRepository.save(user);
        return userMapper.userResponse(user);
    }
}
