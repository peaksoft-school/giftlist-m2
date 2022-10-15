package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.controller.payload.FriendsRequest;
import kg.giftlist.giftlistm2.controller.payload.FriendsResponse;
import kg.giftlist.giftlistm2.db.entity.Notification;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.repository.NotificationRepository;
import kg.giftlist.giftlistm2.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jmx.export.notification.UnableToSendNotificationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class FriendsService {
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;




//        @Transactional
//        public SimpleResponse requestToFriend(Long friendId) {
//            User user = getAuthenticatedUser();
//            User friend = findByUserId(friendId);
//            if (friend.equals(user)) {
//                throw new UserForbiddenException("You can not request to you");
//            }
//            if (friend.getRequestToFriends().contains(user)) {
//                log.error("Request already sent");
//                throw new AlreadyExistException("Request already sent");
//            }
//            friend.addRequestToFriend(user);



    public FriendsResponse requestToFriend(Long friendId){
        User user = getAuthenticatedUser();
        User friend = userRepository.findById(friendId).get();
      if(friend == user){
          log.info("You can't send a request to yourself");
          throw new UsernameNotFoundException("You can't send a request to yourself");
      } else if (friend.getRequestToFriends().contains(user)) {
          log.info("Request already sent");
          throw new UsernameNotFoundException("Request already sent");
      } else if (friend.getFriends().contains(user)) {
          log.info("This user is already in your friends");
          throw  new RuntimeException("This user is already in your friends");
      }

          friend.addRequestToFriend(user);
          log.info("Request to friend successfully send");
        Notification notification = new Notification();
        notification.setCreated(LocalDate.now());
        notification.setUser(user);
        notification.setReceiverId(friendId);
        friend.addNotification(notification);
        notificationRepository.saveAll(friend.getNotifications());
      return mapToFriendResponse(user);
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
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login).get();
    }

}

