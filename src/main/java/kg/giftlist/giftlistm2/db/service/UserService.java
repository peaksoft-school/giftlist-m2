package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.controller.payload.UserRequest;
import kg.giftlist.giftlistm2.controller.payload.UserResponse;

public interface UserService{
  UserResponse register(UserRequest userRequest);
}
