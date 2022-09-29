package kg.giftlist.giftlistm2.db.service.serviceImpl;

import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.repository.UserRepository;
import kg.giftlist.giftlistm2.db.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }

    @Override
    public void updatePassword(User user) {
        User user1 = userRepository.findById(user.getId()).get();
        user1.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user1);
    }
}
