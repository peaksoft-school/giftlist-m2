package kg.giftlist.giftlistm2.mapper;

import kg.giftlist.giftlistm2.controller.payload.UserRequest;
import kg.giftlist.giftlistm2.controller.payload.UserResponse;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring",uses= UserService.class)//mapstruct
public interface UserMapper {
    @Mapping(target = "password",source = "userRequest.confirmPassword")
    User toUser(UserRequest userRequest);
    void updateUserFromUserRequest(UserRequest userRequest, @MappingTarget User user);

    UserResponse userResponse(User user);
}
