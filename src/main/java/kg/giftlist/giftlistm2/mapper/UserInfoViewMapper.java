package kg.giftlist.giftlistm2.mapper;

import kg.giftlist.giftlistm2.controller.payload.response.UserInfoResponse;
import kg.giftlist.giftlistm2.db.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInfoViewMapper {

    public UserInfoResponse viewUserInfo(User user) {
        UserInfoResponse userInfoResponse = new UserInfoResponse();
        userInfoResponse.setFirstName(user.getFirstName());
        userInfoResponse.setLastName(user.getLastName());
        userInfoResponse.setEmail(user.getEmail());
        userInfoResponse.setImage(user.getImage());
        userInfoResponse.setCity(user.getCity());
        userInfoResponse.setDateOfBirth(user.getDateOfBirth());
        userInfoResponse.setPhoneNumber(user.getPhoneNumber());
        userInfoResponse.setClothingSize(user.getClothingSize());
        userInfoResponse.setShoeSize(user.getShoeSize());
        userInfoResponse.setHobby(user.getHobbies());
        userInfoResponse.setImportantNote(user.getImportantToKnow());
        return userInfoResponse;
    }

}
