package kg.giftlist.giftlistm2.db.entity;


import kg.giftlist.giftlistm2.enums.InviteStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "invite")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Invite {
    @Id
    @GeneratedValue(generator = "invite_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "invite_gen", sequenceName = "invite_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    private User userFrom;

    @ManyToOne
    private User userTo;

    @Column(name = "invited_status")
    @Enumerated(EnumType.ORDINAL)
    private InviteStatus inviteStatus;


}