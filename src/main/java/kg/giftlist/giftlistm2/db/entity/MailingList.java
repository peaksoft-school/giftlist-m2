package kg.giftlist.giftlistm2.db.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "mailing_list")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MailingList {

    @Id
    @GeneratedValue(generator = "mailing_list_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "mailing_list_gen", sequenceName = "mailing_list_seq", allocationSize = 1)
    private Long id;

    @Email
    private String email;

    private String header;

    private String image;

    @Size(max = 10000)
    private String text;

    @CreatedDate
    @JsonFormat(pattern = "yyyy.MM.dd")
    private LocalDate createdAt;

}
