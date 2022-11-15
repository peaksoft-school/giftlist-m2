package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.controller.payload.FriendProfileResponse;
import kg.giftlist.giftlistm2.controller.payload.FriendResponse;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.repository.HolidayRepository;
import kg.giftlist.giftlistm2.db.repository.NotificationRepository;
import kg.giftlist.giftlistm2.db.repository.UserRepository;
import kg.giftlist.giftlistm2.exception.UserExistException;
import kg.giftlist.giftlistm2.exception.UserNotFoundException;
import kg.giftlist.giftlistm2.mapper.FriendMapper;
import kg.giftlist.giftlistm2.mapper.FriendMappers;
import kg.giftlist.giftlistm2.validation.ValidationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FriendsService {

    private final UserRepository userRepository;
    private final HolidayRepository holidayRepository;
    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;
    private final FriendMappers friendMappers;

    public List<FriendResponse> getAllFriends() {
        User user = getAuthenticatedUser();
        if (user.getFriends().isEmpty()) {
            throw new UserNotFoundException("Not found your friends");
        }
        return view(userRepository.getAllFriendByUserId(user.getId()));
    }

    public List<FriendResponse> getAllRequestToFriend() {
        User user = getAuthenticatedUser();
        if (user.getRequestToFriends().isEmpty()) {
            throw new UserNotFoundException("Not found your request friend");
        }
        return view(userRepository.getAllRequestToFriend(user.getId()));
    }

    public List<FriendResponse> view(List<User> userList) {
        List<FriendResponse> responses = new ArrayList<>();
        for (User us : userList) {
            responses.add(friendMappers.response(us, holidayRepository.getAllUserHolidays(us.getId()).size(),
                    userRepository.getAllUserWishList(us.getId()).size(), ValidationType.SUCCESSFUL));
        }
        return responses;
    }

    public FriendProfileResponse getFriend(Long friendId) {
        userRepository.findById(friendId).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + friendId));
        return friendMappers.friendResponse(userRepository.getFriendById(friendId));
    }

    public FriendResponse requestToFriend(Long friendId) {
        User user = getAuthenticatedUser();
        User friend = userRepository.findById(friendId).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + friendId));
        if (friend == user) {
            log.info("You can not send a request to yourself ");
            throw new UserExistException("You can not send a request to yourself");
        }
        if (friend.getRequestToFriends().contains(user)) {
            log.error("Request already sent");
            throw new UserExistException("Request already sent");
        }
        if (friend.getFriends().contains(user)) {
            log.info("This user is already in your friends");
            throw new UserExistException("This user is already in your friends");
        }
        friend.sendRequestToFriend(user);
        userRepository.save(friend);
        friend.addNotification(notificationService.sendNotification(user,new ArrayList<>(List.of(friend))));
        notificationRepository.saveAll(friend.getNotifications());

        log.info("Request to friend successfully send");
        return friendMappers.response(friend, holidayRepository.getAllUserHolidays(friendId).size(),
                userRepository.getAllUserWishList(friendId).size(), ValidationType.REQUEST_SUCCESSFULLY_SENT);
    }

    public FriendResponse acceptToFriend(Long friendId) {
        User user = getAuthenticatedUser();
        User friend = userRepository.findById(friendId).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + friendId));
        if (user == friend) {
            throw new UserExistException("You can't add yourself as a friend");
        }
        if (!(user.getRequestToFriends().contains(friend))) {
            throw new UserNotFoundException("Your al ready friends");
        } else {
            user.addUserToFriend(friend);
            friend.addUserToFriend(user);
            user.getRequestToFriends().remove(friend);
            userRepository.save(friend);
            friend.addNotification(notificationService.acceptSendNotification(user,new ArrayList<>(List.of(friend))));
            notificationRepository.saveAll(friend.getNotifications());
        }
        return friendMappers.response(friend, holidayRepository.getAllUserHolidays(friendId).size(),
                userRepository.getAllUserWishList(friendId).size(), ValidationType.ACCEPTED);
    }

    public String declineFriendRequest(Long friendId) {
        User user = getAuthenticatedUser();
        User friend = userRepository.findById(friendId).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + friendId));
        if (!(user.getRequestToFriends().contains(friend))) {
            log.error("Тo friend requests found from user with id: " + friendId);
            throw new UserNotFoundException("Тo friend requests found from user with id: " + friendId);
        } else {
            user.getRequestToFriends().remove(friend);
            userRepository.save(user);
            return "Friend request successfully denied";
        }
    }

    public String deleteFriend(Long friendId) {
        User user = getAuthenticatedUser();
        User friend = userRepository.findById(friendId).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + friendId));
        if (!(user.getFriends().contains(friend))) {
            log.error("You have not friend with id: " + friendId);
            throw new UserNotFoundException("You have not friend with id: " + friendId);
        } else {
            user.getFriends().remove(friend);
            friend.getFriends().remove(user);
            user.sendRequestToFriend(friend);
            userRepository.save(friend);
            return "Successfully deleted friend with email: " + friend.getEmail();
        }
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login);
    }

}


