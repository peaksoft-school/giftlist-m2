package kg.giftlist.giftlistm2.mapper;

import kg.giftlist.giftlistm2.controller.payload.UserInfoRequest;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInfoEditMapper {

    public void update(User user, UserInfoRequest userInfoRequest) {
        user.setCity(userInfoRequest.getCity());
        user.setDateOfBirth(userInfoRequest.getDateOfBirth());
        user.setPhoneNumber(userInfoRequest.getPhoneNumber());
        user.setClothingSize(userInfoRequest.getClothingSize());
        user.setShoeSize(userInfoRequest.getShoeSize());
        user.setHobbies(userInfoRequest.getHobby());
    }
}
