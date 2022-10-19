package kg.giftlist.giftlistm2.db.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import kg.giftlist.giftlistm2.controller.payload.Response;
import kg.giftlist.giftlistm2.db.entity.Notification;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.repository.NotificationRepository;
import kg.giftlist.giftlistm2.db.repository.UserRepository;
import kg.giftlist.giftlistm2.exception.MyException;
import kg.giftlist.giftlistm2.validation.ValidationType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FriendsService {
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final FirebaseMessaging firebaseMessaging;


FirebaseMessaging firebaseMessaging() throws IOException {
    GoogleCredentials googleCredentials = GoogleCredentials
            .fromStream(new ClassPathResource("firebase-service-account.json").getInputStream());
    FirebaseOptions firebaseOptions = FirebaseOptions
            .builder()
            .setCredentials(googleCredentials)
            .build();
    FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions, "my-app");
    return FirebaseMessaging.getInstance(app);
}
    public Response getAllFriends(){
        User user = getAuthenticatedUser();
        List<User>friends = user.getFriends();
        List<Response>responses = new ArrayList<>();
        for (User us : friends){
            Response resp = FriendMapper.INSTANCE.response(us,ValidationType.SUCCESSFUL);
            responses.add(resp);
        }
        return (Response) responses;
    }
    public Response requestToFriend(Long friendId) {
        User user = getAuthenticatedUser();
        User friend = userRepository.findById(friendId).get();
        if (friend == user) {
            log.info("You can't send a request to yourself");
            throw new MyException("You can't send a request to yourself");
        } else if (friend.getRequestToFriends().contains(user)) {
            log.info("Request already sent");
            throw new MyException("Request already sent");
        } else if (friend.getFriends().contains(user)) {
            log.info("This user is already in your friends");
            throw new MyException("This user is already in your friends");
        }
        friend.addRequestToFriend(user);
        log.info("Request to friend successfully send");
        friend.addNotification(sendNotification(user, friendId));
        notificationRepository.saveAll(friend.getNotifications());
        return FriendMapper.INSTANCE.response(friend,ValidationType.SUCCESSFUL);
    }


    public void deleteFriend(Long friendId) {
        User user = getAuthenticatedUser();
        User friend = userRepository.findById(friendId).get();
        if (user.getFriends().contains(friend)) {
            friend.getFriends().remove(user);
            user.getFriends().remove(friend);
            log.info("Successfully deleted user with name ");
            throw new MyException("Successfully deleted user with email "+friend.getEmail());

        } else {
            log.error("You have not friend with id ");
            throw new MyException("You have not friend with id "+friend.getId());
        }

    }

    public Response refuseRequestToFriend(Long friendId) {
        User user = getAuthenticatedUser();
        User friend = userRepository.findById(friendId).get();
        if (friend.getRequestToFriends().contains(user)) {
            friend.getRequestToFriends().remove(user);
        } else {
            throw new MyException("No request to friend");
        }
        log.info("Request to friend successfully refused");
        return FriendMapper.INSTANCE.response(friend,ValidationType.SUCCESSFUL);
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
        return FriendMapper.INSTANCE.response(friend,ValidationType.SUCCESSFUL);
    }

    private Notification sendNotification(User user, Long friendId) {
        Notification notification = new Notification();
        notification.setCreated(LocalDate.now());
        notification.setUser(user);
        notification.setReceiverId(friendId);
        return notification;
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login);
    }
}

