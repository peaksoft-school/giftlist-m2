package kg.giftlist.giftlistm2.mapper;

import kg.giftlist.giftlistm2.controller.payload.LoginResponse;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.enums.Role;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
public class LoginMapper {

    public LoginResponse loginView(String token, String message, User user) {
        var loginResponse = new LoginResponse();
        if (user != null) {
            setAuthority(loginResponse, Collections.singletonList(user.getRole()));
        }
        loginResponse.setJwtToken(token);
        loginResponse.setMessage(message);
        loginResponse.setId(user.getId());
        loginResponse.setEmail(user.getEmail());
        return loginResponse;
    }

    public void setAuthority(LoginResponse loginResponse, List<Role> roles) {
        Set<String> authorities = new HashSet<>();
        for (Role role : roles) {
            authorities.add(role.getAuthority());
        }
        String join = String.join("", authorities);
        loginResponse.setAuthorities(join);
    }

    }



