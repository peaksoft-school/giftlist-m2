package kg.giftlist.giftlistm2.controller.payload;

import kg.giftlist.giftlistm2.db.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendsRequest {
    private Long userId;
    private Long friendId;

}
