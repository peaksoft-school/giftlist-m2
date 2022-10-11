package kg.giftlist.giftlistm2.db.entity;


import kg.giftlist.giftlistm2.enums.InviteStatus;

import javax.persistence.*;

@Entity
    class Invite {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @Column
        private User from;
        @Column
        private User to;

        @Enumerated(EnumType.ORDINAL)
        private InviteStatus status;
    }
}
