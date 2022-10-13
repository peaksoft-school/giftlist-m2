package kg.giftlist.giftlistm2.mapper;

import kg.giftlist.giftlistm2.controller.payload.LoginResponse;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.enums.Role;
import kg.giftlist.giftlistm2.validation.ValidationType;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class LoginMapper {

    public LoginResponse loginView(String token, String message, User user) {
        var loginResponse = new LoginResponse();
        if (user != null) {
            try {
                setAuthority(loginResponse, Collections.singletonList(user.getRole()));
            }
            catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
            loginResponse.setId(user.getId());
            loginResponse.setFirstName(user.getFirstName());
            loginResponse.setLastName(user.getLastName());
            loginResponse.setEmail(user.getEmail());
            loginResponse.setJwtToken(token);
            loginResponse.setMessage(message);
            return loginResponse;
        } else {
            loginResponse.setMessage(ValidationType.LOGIN_FAILED);
            return loginResponse;
        }
    }

    public void setAuthority(LoginResponse loginResponse, List<Role> roles) {
        Set<String> authorities = new HashSet<>();
        for (Role role : roles) {
            if (role != null) {
                authorities.add(role.getAuthority());
            }
            else throw new RuntimeException("");
        }
        String join = String.join("", authorities);
        loginResponse.setAuthorities(join);
    }

}
