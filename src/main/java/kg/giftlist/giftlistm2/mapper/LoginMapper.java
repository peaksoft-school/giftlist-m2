package kg.giftlist.giftlistm2.mapper;

import kg.giftlist.giftlistm2.controller.payload.LoginResponse;
import kg.giftlist.giftlistm2.controller.payload.UserRequest;
import kg.giftlist.giftlistm2.controller.payload.UserResponse;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.service.UserService;
import kg.giftlist.giftlistm2.enums.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Mapper(componentModel = "spring",uses= UserService.class)//mapstruct
public class LoginMapper {

    public LoginResponse loginView(String token, String message, User user) {
        var loginResponse = new LoginResponse();
        if (user != null) {
            setAuthority(loginResponse, Collections.singletonList(user.getRole()));
        }
        loginResponse.setJwtToken(token);
        loginResponse.setMessage(message);
        return loginResponse;
    }

    public void setAuthority(LoginResponse loginResponse, List<Role> roles) {
        Set<String> authorities = new HashSet<>();
        for (Role role : roles) {
            authorities.add(role.getAuthority());
        }
        loginResponse.setAuthorities(authorities);
    }
    public interface UserMapper {
        @Mapping(target = "password",source = "userRequest.confirmPassword")
        User toUser(UserRequest userRequest);
        void updateUserFromUserRequest(UserRequest userRequest, @MappingTarget User user);

        UserResponse userResponse(User user);

    }
}


