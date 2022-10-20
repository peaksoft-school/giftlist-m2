package kg.giftlist.giftlistm2.mapper;

import kg.giftlist.giftlistm2.controller.payload.SignupRequest;
import kg.giftlist.giftlistm2.controller.payload.SignupResponse;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.service.impl.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = UserService.class)
public interface UserSignUpMapper {
    @Mapping(target = "password", source = "signupRequest.confirmPassword")
     User toUser(SignupRequest signupRequest);
    SignupResponse signupResponse(User user);

}
