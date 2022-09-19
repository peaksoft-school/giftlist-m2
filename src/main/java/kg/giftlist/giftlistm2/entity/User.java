package kg.giftlist.giftlistm2.entity;

import kg.giftlist.giftlistm2.enums.ClothingSize;
import kg.giftlist.giftlistm2.enums.Role;
import kg.giftlist.giftlistm2.enums.ShoeSize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(generator = "user_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_gen", sequenceName = "user_seq", allocationSize = 1)

    private Long id;

    private String name;

    private String surname;

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

    private String hobbies;

    @Size(max = 10000)
    @Column(name = "important_to_know")
    private String importantToKnow;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user")
    private List<WishList> wishLists;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Charity> charities;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "blockedBy")
    private List<Booking> bookings;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Holiday> holidays;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Complaints> complaints;

}
