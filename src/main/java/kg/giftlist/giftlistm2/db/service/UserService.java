package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.controller.payload.SignupRequest;
import kg.giftlist.giftlistm2.controller.payload.SignupResponse;

public interface UserService{

  SignupResponse register(SignupRequest signupRequest);


}
