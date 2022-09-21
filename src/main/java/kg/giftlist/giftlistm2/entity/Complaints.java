package kg.giftlist.giftlistm2.entity;

import kg.giftlist.giftlistm2.enums.ComplaintsType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "complaints")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Complaints {
    @Id
    @GeneratedValue(generator = "complaints_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "complaints_gen", sequenceName = "complaints_seq", allocationSize = 1)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ComplaintsType complaintsType;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
