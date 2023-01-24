package kg.giftlist.giftlistm2.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clothing_size")
public class ClothingSize {

    @Id
    private Long id;

    private String size;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

}
