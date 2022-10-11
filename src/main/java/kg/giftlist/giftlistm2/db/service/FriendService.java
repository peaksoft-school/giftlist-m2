package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.controller.payload.FriendsRequest;
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
public class FriendService {
    private final UserRepository userRepository;
    Set<User> friends = new HashSet<>();

//    public void addFriends(User user){
//        if (friends ==null){
//            friends = new HashSet<>();
//        }
//        friends.add(user);
//    }
    public  void add(FriendsRequest request){
        Optional<User> user = userRepository.findById(request.getUser().getId());
        Set<Optional<User>> friends = new HashSet<>();
        friends.add(user);



    }

}
