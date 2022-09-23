package kg.giftlist.giftlistm2.service;

import kg.giftlist.giftlistm2.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByResetToken(String resetToken);
    void saveUser(User user);


}
