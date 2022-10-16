package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.controller.payload.Response;
import kg.giftlist.giftlistm2.db.entity.Notification;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.repository.NotificationRepository;
import kg.giftlist.giftlistm2.db.repository.UserRepository;
import kg.giftlist.giftlistm2.exception.MyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FriendsService {
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
//   private final FCMInitializer fcmSettings;


//public List<TeacherResponse> getAllTeachers() {
//    List<User> users = userRepository.getAllByRoles("TEACHER");
//    List<TeacherResponse> responses = new ArrayList<>();
//    for (User us : users) {
//        TeacherResponse resp = mapToTeacherResponse(us);
//        responses.add(resp);
//    }
//    return responses;
//}
    public Response getAllFriends(){
        User user = getAuthenticatedUser();
        List<User>friends = user.getFriends();
        List<Response>responses = new ArrayList<>();
        for (User us : friends){
            Response resp = FriendMapper.INSTANCE.response(us);
            responses.add(resp);
        }
        return (Response) responses;
    }
    public Response requestToFriend(Long friendId) {
        User user = getAuthenticatedUser();
        User friend = userRepository.findById(friendId).get();
        if (friend == user) {
            log.info("You can't send a request to yourself");
            throw new NotFoundException("You can't send a request to yourself");
        } else if (friend.getRequestToFriends().contains(user)) {
            log.info("Request already sent");
            throw new MyException("Request already sent");
        } else if (friend.getFriends().contains(user)) {
            log.info("This user is already in your friends");
            throw new RuntimeException("This user is already in your friends");
        }
        friend.addRequestToFriend(user);
        log.info("Request to friend successfully send");
        friend.addNotification(sendNotification(user, friendId));
        notificationRepository.saveAll(friend.getNotifications());
        return FriendMapper.INSTANCE.response(friend);
    }

    private Notification sendNotification(User user, Long friendId) {
        Notification notification = new Notification();
        notification.setCreated(LocalDate.now());
        notification.setUser(user);
        notification.setReceiverId(friendId);
        return notification;
    }

    public void deleteFriend(Long friendId) {
        User user = getAuthenticatedUser();
        User friend = userRepository.findById(friendId).get();
        if (user.getFriends().contains(friend)) {
            friend.getFriends().remove(user);
            user.getFriends().remove(friend);
            log.info("Successfully deleted user with name ");
        } else {
            log.error("You have not friend with id ");
            throw new NotFoundException("You have not friend with id");
        }

    }

    public Response refuseRequestToFriend(Long friendId) {
        User user = getAuthenticatedUser();
        User friend = userRepository.findById(friendId).get();
        if (friend.getRequestToFriends().contains(user)) {
            friend.getRequestToFriends().remove(user);
        } else {
            throw new NotFoundException("No request to friend");
        }
        log.info("Request to friend successfully refused");
        return FriendMapper.INSTANCE.response(friend);
    }

    public Response acceptToFriend(Long friendId) {
        User user = getAuthenticatedUser();
        User friend = userRepository.findById(friendId).get();
        if (user.getRequestToFriends().contains(friend)) {
            friend.acceptToFriend(user);
            user.getRequestToFriends().remove(friend);
            user.acceptToFriend(friend);
            userRepository.save(friend);
        } else {
            throw new MyException("You are already friend");
        }
        log.info("Successfully accept to friend");
        return FriendMapper.INSTANCE.response(friend);
    }


    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login).get();
    }
}

