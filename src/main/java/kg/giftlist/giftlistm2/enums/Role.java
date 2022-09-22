package kg.giftlist.giftlistm2.enums;

import kg.giftlist.giftlistm2.entity.User;
import org.springframework.security.core.GrantedAuthority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public enum Role implements GrantedAuthority {
    ADMIN,
    USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
