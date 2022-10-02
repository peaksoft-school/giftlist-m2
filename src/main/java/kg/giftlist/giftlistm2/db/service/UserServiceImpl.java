package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }

    public void updatePassword(User user) {
        User user1 = userRepository.findById(user.getId()).get();
        user1.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user1);
    }

}
