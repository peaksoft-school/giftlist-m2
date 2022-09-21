package kg.giftlist.giftlistm2.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN(Code.ADMIN),
    USER(Code.USER);

    private final String authority;

    Role(String authority) {
        this.authority = authority;
    }

    //    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String roleName;
//
//    @ManyToMany(targetEntity = User.class, mappedBy = "roles", cascade = {CascadeType.MERGE,
//    CascadeType.REFRESH, CascadeType.DETACH, CascadeType.PERSIST})
//    private List<User> users;

    @Override
    public String getAuthority() {
        return authority;
    }

    public class Code {
        public static final String ADMIN = "ADMIN";
        public static final String USER = "USER";
    }
}
