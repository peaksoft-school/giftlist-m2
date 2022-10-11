package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.controller.payload.FriendsRequest;
import kg.giftlist.giftlistm2.controller.payload.FriendsResponse;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class FriendsService {
    private final UserRepository userRepository;


    //    public void addFriends(User user){
//        if (friends ==null){
//            friends = new HashSet<>();
//        }
//        friends.add(user);
//    }
    public FriendsResponse add(FriendsRequest request){
        User user1 =userRepository.findById(request.getFriendId()).get();
        userRepository.save(user1);
        return mapToFriendResponse(user1);
    }

    private FriendsResponse mapToFriendResponse(User user) {
        return FriendsResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .city(user.getCity())
                .clothingSize(user.getClothingSize())
                .shoeSize(user.getShoeSize())
                .hobbies(user.getHobbies())
                .importantToKnow(user.getImportantToKnow())
                .wishLists(user.getWishLists())
                .charities(user.getCharities())
                .holidays(user.getHolidays())
                .build();
    }

}

