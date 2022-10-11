package kg.giftlist.giftlistm2.db.entity;


import kg.giftlist.giftlistm2.enums.InviteStatus;

import javax.persistence.*;

@Entity
    class Invite {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @Column(name = "user_from")
        @ManyToOne
        private User userFrom;
        @Column(name = "user_to")
        @ManyToOne
        private User userTo;

        @Enumerated(EnumType.ORDINAL)
        private InviteStatus status;
    }

