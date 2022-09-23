package kg.giftlist.giftlistm2.service.serviceImpl;

import kg.giftlist.giftlistm2.entity.User;
import kg.giftlist.giftlistm2.repository.UserRepository;
import kg.giftlist.giftlistm2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service("userService")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findUserByResetToken(String resetToken) {
        return userRepository.findByResetToken(resetToken);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }
}
