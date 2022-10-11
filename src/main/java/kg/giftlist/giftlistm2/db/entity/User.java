package kg.giftlist.giftlistm2.db.entity;

import kg.giftlist.giftlistm2.enums.ClothingSize;
import kg.giftlist.giftlistm2.enums.Role;
import kg.giftlist.giftlistm2.enums.ShoeSize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(generator = "user_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_gen", sequenceName = "user_seq", allocationSize = 1)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Email
    private String email;

    private String password;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "clothing_size")
    private ClothingSize clothingSize;

    @Enumerated(EnumType.STRING)
    @Column(name = "shoe_size")
    private ShoeSize shoeSize;

    @Size(max = 10000)
    private String hobbies;

    private String city;

    @Size(max = 10000)
    @Column(name = "important_to_know")
    private String importantToKnow;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
    private List<WishList> wishLists;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Charity> charities;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private List<Booking> bookings;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Holiday> holidays;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Complaints> complaints;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userFrom")
    private List<Invite> invites;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userTo")
    private List<Invite> invite;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(
            name = "friends",
            joinColumns = @JoinColumn(name = "user_one"),
            inverseJoinColumns = @JoinColumn(name = "user_two")
    )
    private Set<User> friends;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> grantedAuthorities = new LinkedList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        return grantedAuthorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
