package kg.giftlist.giftlistm2.mapper;

import kg.giftlist.giftlistm2.controller.payload.FriendProfileResponse;
import kg.giftlist.giftlistm2.controller.payload.FriendResponse;
import kg.giftlist.giftlistm2.db.entity.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FriendMapper {

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
        List<WishList> wishLists = user.getWishLists();
        if (wishLists != null) {
            friendProfileResponse.wishLists(new ArrayList<WishList>(wishLists));
        }
        List<Charity> charities = user.getCharities();
        if (charities != null) {
            friendProfileResponse.charities(new ArrayList<Charity>(charities));
        }
        List<Holiday> holidays = user.getHolidays();
        if (holidays != null) {
            friendProfileResponse.holidays(new ArrayList<Holiday>(holidays));
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
