package kg.giftlist.giftlistm2.db.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Entity
@Table(name = "mailing_list")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MailingList {
    @Id
    @GeneratedValue(generator = "mailing_list_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "mailing_list_gen", sequenceName = "mailing_list_seq", allocationSize = 1)
    private Long id;

    private String userName;

    @Email
    private String email;

    private String header;

    private String image;

    @Size(max = 10000)
    private String text;

}
