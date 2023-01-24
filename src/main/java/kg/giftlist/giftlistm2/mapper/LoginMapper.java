package kg.giftlist.giftlistm2.mapper;

import kg.giftlist.giftlistm2.controller.payload.response.AuthResponse;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.enums.Role;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class LoginMapper {

    public AuthResponse loginView(String token, String message, User user) {
        var loginResponse = new AuthResponse();
        if (user != null) {
            try {
                setAuthority(loginResponse, Collections.singletonList(user.getRole()));
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
            loginResponse.setId(user.getId());
            loginResponse.setFirstName(user.getFirstName());
            loginResponse.setLastName(user.getLastName());
            loginResponse.setEmail(user.getEmail());
            loginResponse.setJwtToken(token);
            loginResponse.setMessage(message);
        }
        return loginResponse;
    }

    public void setAuthority(AuthResponse loginResponse, List<Role> roles) {
        Set<String> authorities = new HashSet<>();
        for (Role role : roles) {
            if (role != null) {
                authorities.add(role.getAuthority());
            } else throw new RuntimeException("");
        }
        String join = String.join("", authorities);
        loginResponse.setAuthorities(join);
    }

}
