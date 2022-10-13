package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.controller.payload.LoginRequest;
import kg.giftlist.giftlistm2.controller.payload.LoginResponse;
import kg.giftlist.giftlistm2.controller.payload.SignupRequest;
import kg.giftlist.giftlistm2.controller.payload.SignupResponse;
import org.springframework.http.ResponseEntity;

public interface UserService{

  SignupResponse register(SignupRequest signupRequest);

  ResponseEntity <LoginResponse> login(LoginRequest loginRequest);


}
