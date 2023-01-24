package kg.giftlist.giftlistm2.mapper;

import kg.giftlist.giftlistm2.controller.payload.request.UserInfoRequest;
import kg.giftlist.giftlistm2.db.entity.ShoeSize;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.repository.ShoeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserInfoEditMapper {

    private final ShoeRepository shoeRepository;

    public void update(User user, UserInfoRequest userInfoRequest) {
        List<ShoeSize> shoeSize = shoeRepository.getDefaultSize();
        user.setCity(userInfoRequest.getCity());
        user.setDateOfBirth(userInfoRequest.getDateOfBirth());
        user.setPhoneNumber(userInfoRequest.getPhoneNumber());
        user.setClothingSize(userInfoRequest.getClothingSize());
        if (userInfoRequest.getShoeSize() == null) {
            user.setShoeSize(shoeSize);
            shoeSize.forEach(a -> a.setUser(user));
        } else user.setShoeSize(userInfoRequest.getShoeSize());
        shoeSize.forEach(a -> a.setUser(user));
        user.setHobbies(userInfoRequest.getHobby());
    }

}
