package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.controller.payload.AuthRequest;
import kg.giftlist.giftlistm2.controller.payload.AuthResponse;
import kg.giftlist.giftlistm2.controller.payload.SignupRequest;


public interface UserService{

  AuthResponse register(SignupRequest signupRequest);

  AuthResponse login(AuthRequest loginRequest);

}
