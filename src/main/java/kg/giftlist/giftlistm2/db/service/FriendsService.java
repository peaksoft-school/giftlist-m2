package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.controller.payload.Response;
import kg.giftlist.giftlistm2.db.entity.Notification;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.repository.NotificationRepository;
import kg.giftlist.giftlistm2.db.repository.UserRepository;
import kg.giftlist.giftlistm2.enums.NotificationStatus;
import kg.giftlist.giftlistm2.exception.MyException;
import kg.giftlist.giftlistm2.validation.ValidationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FriendsService {
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;


    public Response getAllFriends() {
        User user = getAuthenticatedUser();
        List<User> friends = user.getFriends();
        List<Response> responses = new ArrayList<>();
        for (User us : friends) {
            Response resp = FriendMapper.INSTANCE.response(us, ValidationType.SUCCESSFUL);
            responses.add(resp);
        }
        return (Response) responses;
    }

    public Response requestToFriend(Long friendId) {
        User user = getAuthenticatedUser();
        User friend = userRepository.findById(friendId).get();
        if (friend == user) {
            log.info("You can not send a request to yourself ");
            throw new MyException("You can not send a request to yourself");
        } else if (friend.getRequestToFriends().contains(user)) {
            log.info("Request already sent");
            throw new MyException("Request already sent");
        } else if (friend.getFriends().contains(user)) {
            log.info("This user is already in your friends");
            throw new MyException("This user is already in your friends");
        }
        friend.sendRequestToFriend(user);
        log.info("Request to friend successfully send");
        return FriendMapper.INSTANCE.response(friend, ValidationType.SUCCESSFUL);
    }

    public Response acceptToFriend(Long friendId) {
        User user = getAuthenticatedUser();
        User friend = userRepository.findById(friendId).get();
        if (user.getRequestToFriends().contains(friend)) {
            user.addUserToFriend(friend);
            friend.addUserToFriend(user);
            user.getRequestToFriends().remove(friend);
            userRepository.save(friend);
        } else {
            throw new MyException("You are already friend");
        }
        log.info("Successfully accept to friend");
        return FriendMapper.INSTANCE.response(friend, ValidationType.SUCCESSFUL);
    }

    public String refuseRequestToFriend(Long friendId) {
        User user = getAuthenticatedUser();
        User friend = userRepository.findById(friendId).get();
        if (friend.getRequestToFriends().contains(user)) {
            friend.getRequestToFriends().remove(user);
        } else {
            throw new MyException("Тo friend requests found from user with id: "+friendId);
        }
        log.info("Request to friend successfully refused");
        return "Request to friend successfully refused";
    }

    public String deleteFriend(Long friendId) {
        User user = getAuthenticatedUser();
        User friend = userRepository.findById(friendId).get();
        if (user.getFriends().contains(friend)) {
            user.getFriends().remove(friend);
            return "Successfully deleted friend with email: "+friend.getEmail();
        } else {
            log.error("You have not friend with id: "+friendId);
            throw new MyException("You have not friend with id: " + friend.getId());
        }

    }

    private Notification sendNotification(User user, Long friendId) {
        Notification notification = new Notification();
        notification.setCreated(LocalDate.now());
        notification.setUser(user);
        notification.setReceiverId(friendId);
        notification.setNotificationStatus(NotificationStatus.REQUEST_TO_FRIEND);
        return notification;

    }


    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());
        String login = authentication.getName();
        return userRepository.findByEmail(login);
    }

}


