package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.controller.payload.HolidayResponse;
import kg.giftlist.giftlistm2.controller.payload.Response;
import kg.giftlist.giftlistm2.controller.payload.ResponseFriend;
import kg.giftlist.giftlistm2.db.entity.Holiday;
import kg.giftlist.giftlistm2.db.entity.Notification;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.repository.HolidayRepository;
import kg.giftlist.giftlistm2.db.repository.NotificationRepository;
import kg.giftlist.giftlistm2.db.repository.UserRepository;
import kg.giftlist.giftlistm2.enums.NotificationStatus;
import kg.giftlist.giftlistm2.exception.UserExistException;
import kg.giftlist.giftlistm2.exception.UserNotFoundException;
import kg.giftlist.giftlistm2.validation.ValidationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FriendsService {
    private final UserRepository userRepository;
    private final HolidayRepository holidayRepository;

    public List<ResponseFriend>view(List<User>userList,int count) {
        List<ResponseFriend> responses = new ArrayList<>();

        for (User us : userList) {
            responses.add(FriendMapper.INSTANCE.friendResonse(us,count));
        }
        return  responses;
    }
    public List<ResponseFriend> getAllFriends(){
        User user = getAuthenticatedUser();
        return view(userRepository.getAllFriendByUserId(user.getId()),
        66);
    }
    public List<ResponseFriend> getAllRequestToFriend(){
        User user = getAuthenticatedUser();
        return view(userRepository.getAllRequestToFriend(user.getId()),
                holidayRepository.getAllUserHolidays(user.getId()).size());
    }


    public Response requestToFriend(Long friendId) {
        User user = getAuthenticatedUser();
        User friend = userRepository.findById(friendId).orElseThrow(
                () -> new UserNotFoundException("User not found with id: "+friendId));
        if (friend == user) {
            log.info("You can not send a request to yourself ");
            throw new UserExistException("You can not send a request to yourself");
        } else if (friend.getRequestToFriends().contains(user)) {
            log.info("Request already sent");
            throw new UserExistException("Request already sent");
        } else if (friend.getFriends().contains(user)) {
            log.info("This user is already in your friends");
            throw new UserExistException("This user is already in your friends");
        }
        friend.sendRequestToFriend(user);
        userRepository.save(friend);
        log.info("Request to friend successfully send");
        return FriendMapper.INSTANCE.response(friend, ValidationType.REQUEST_SUCCESSFULLY_SENT);
    }

    public Response acceptToFriend(Long friendId) {
        User user = getAuthenticatedUser();
        User friend = userRepository.findById(friendId).orElseThrow(
                () -> new UserNotFoundException("User not found with id: "+friendId));
        if (user == friend){
            throw new UserExistException("You can't add yourself as a friend");
        }
        if (!(user.getRequestToFriends().contains(friend))) {
            throw new UserNotFoundException("not found");
        }
        else {
            user.addUserToFriend(friend);
            friend.addUserToFriend(user);
            user.getRequestToFriends().remove(friend);
            userRepository.save(friend);
        }
        return FriendMapper.INSTANCE.response(friend, ValidationType.ACCEPTED);
    }

    public String declineFriendRequest(Long friendId) {
        User user = getAuthenticatedUser();
        User friend = userRepository.findById(friendId).orElseThrow(
                () -> new UserNotFoundException("User not found with id: "+friendId));
        if (user == friend){
            log.info("User not found");
            throw new UsernameNotFoundException("User not found with id "+friendId);
        }
        if (user.getRequestToFriends().contains(friend)) {
            user.getRequestToFriends().remove(friend);
            userRepository.save(user);
        } else {
            throw new UsernameNotFoundException("Тo friend requests found from user with id: " + friendId);
        }
        log.info("Request to friend successfully refused");
        return "Request to friend successfully refused whit email "+friend.getEmail();
    }

    public String deleteFriend(Long friendId) {
        User user = getAuthenticatedUser();
        User friend = userRepository.findById(friendId).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id: "+friendId));
        if (user == friend){
            throw new UsernameNotFoundException("User not found with id "+friendId);
        }
        if (user.getFriends().contains(friend)) {
            user.getFriends().remove(friend);
            user.sendRequestToFriend(friend);
            friend.getFriends().remove(user);
            userRepository.save(user);
            return "Successfully deleted friend with email: " + friend.getEmail();
        } else {
            log.error("You have not friend with id: " + friendId);
            throw new UsernameNotFoundException("You have not friend with id: " + friendId);
        }

    }


    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login);
    }

}


