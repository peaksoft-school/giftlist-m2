package kg.giftlist.giftlistm2.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "wish_list_id")
    @JsonIgnore
    private WishList wishListId;

    @ManyToOne
    @JoinColumn(name = "charity_id")
    @JsonIgnore
    private Charity charityId;

}
