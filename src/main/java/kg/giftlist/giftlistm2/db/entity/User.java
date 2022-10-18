package kg.giftlist.giftlistm2.db.entity;

import kg.giftlist.giftlistm2.enums.ClothingSize;
import kg.giftlist.giftlistm2.enums.InviteStatus;
import kg.giftlist.giftlistm2.enums.Role;
import kg.giftlist.giftlistm2.enums.ShoeSize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(generator = "user_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_gen", sequenceName = "user_seq", allocationSize = 1, initialValue = 3)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String image;
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
    private List<Invite> userFrom;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userTo")
    private List<Invite> userTo;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Notification> notifications;

    @Transient
    @Enumerated(EnumType.STRING)
    private InviteStatus inviteStatus;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "friends")
            private List<User> friends = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "request_to_friend",
            joinColumns = @JoinColumn(name = "user_to"),
            inverseJoinColumns = @JoinColumn(name = "user_from")
    )
    private List<User> requestToFriends = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> grantedAuthorities = new LinkedList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        return grantedAuthorities;
    }

    //    public void addRequestToFriend(User user){
//        if (requestToFriends == null){
//            requestToFriends = new HashSet<>();
//        }
//        requestToFriends.add(user);
//    }
    public void addRequestToFriend(User user) {
        if (CollectionUtils.isEmpty(requestToFriends)) {
            requestToFriends = new ArrayList<>();
        }
        requestToFriends.add(user);
    }

    public void acceptToFriend(User user) {
        if (CollectionUtils.isEmpty(friends)) {
            friends = new ArrayList<>();
        }
        friends.add(user);
    }
    public void addNotification(Notification notification){
        notifications.add(notification);
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
