package kg.giftlist.giftlistm2.db.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "password_reset_token")
public class ResetPasswordToken {

    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(generator = "password_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "password_gen", sequenceName = "password_seq", allocationSize = 1)
    private Long id;

    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User user;

    private LocalDateTime expirationTime;

}