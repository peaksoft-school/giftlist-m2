package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.db.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByResetToken(String resetToken);

    void saveUser(User user);
}
