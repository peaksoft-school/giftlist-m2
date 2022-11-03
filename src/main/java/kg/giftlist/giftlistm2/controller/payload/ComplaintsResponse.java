package kg.giftlist.giftlistm2.controller.payload;

import kg.giftlist.giftlistm2.db.entity.Complaints;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ComplaintsResponse {

    private Complaints complaints;

}
