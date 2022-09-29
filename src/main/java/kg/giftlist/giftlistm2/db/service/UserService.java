package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.db.entity.User;

public interface UserService {
   User  findUserByEmail(String email);

    void updatePassword(User user);
}
