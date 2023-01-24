package kg.giftlist.giftlistm2.mapper;

import kg.giftlist.giftlistm2.controller.payload.response.FriendProfileResponse;
import kg.giftlist.giftlistm2.controller.payload.response.FriendResponse;
import kg.giftlist.giftlistm2.db.entity.Charity;
import kg.giftlist.giftlistm2.db.entity.Holiday;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.entity.WishList;
import kg.giftlist.giftlistm2.db.repository.CharityRepository;
import kg.giftlist.giftlistm2.db.repository.WishListRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class FriendMapper {

    private final CharityRepository charityRepository;
    private final WishListRepository wishListRepository;

    public FriendProfileResponse friendResponse(User user) {
        if (user == null) {
            return null;
        }
        FriendProfileResponse.FriendProfileResponseBuilder friendProfileResponse = FriendProfileResponse.builder();
        friendProfileResponse.id(user.getId());
        friendProfileResponse.firstName(user.getFirstName());
        friendProfileResponse.lastName(user.getLastName());
        friendProfileResponse.email(user.getEmail());
        friendProfileResponse.image(user.getImage());
        friendProfileResponse.dateOfBirth(user.getDateOfBirth());
        friendProfileResponse.phoneNumber(user.getPhoneNumber());
        friendProfileResponse.city(user.getCity());
        friendProfileResponse.clothingSize(user.getClothingSize());
        friendProfileResponse.shoeSize(user.getShoeSize());
        friendProfileResponse.hobbies(user.getHobbies());
        friendProfileResponse.importantToKnow(user.getImportantToKnow());
        List<WishList> wishLists = wishListRepository.getWishLists(user.getId());
        if (wishLists != null) {
            friendProfileResponse.wishLists(wishLists);
        }
        List<Charity> charities = charityRepository.getCharities(user.getId());
        if (charities != null) {
            friendProfileResponse.charities(charities);
        }
        List<Holiday> holidays = user.getHolidays();
        if (holidays != null) {
            friendProfileResponse.holidays(holidays);
        }
        return friendProfileResponse.build();
    }

    public FriendResponse response(User user, int holidayCount, int wishListCount, String message) {
        if (user == null && message == null) {
            return null;
        }
        FriendResponse friendResponse = new FriendResponse();
        if (user != null) {
            friendResponse.setId(user.getId());
            friendResponse.setFirstName(user.getFirstName());
            friendResponse.setLastName(user.getLastName());
            friendResponse.setEmail(user.getEmail());
        }
        if (message != null) {
            friendResponse.setMessage(message);
        }
        friendResponse.setHolidayCount(holidayCount);
        friendResponse.setWishListCount(wishListCount);
        return friendResponse;
    }

}
