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
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.util.*;

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

//    public void removeByID(int id) {
//        try {
//            User user1 = userDao.getUsers()
//                    .stream()
//                    .filter(user -> user.getId() == id)
//                    .findFirst()
//                    .orElseThrow(() -> new MyException("Oops...we don't have such id for removing"));
//            userDao.getUsers().remove(user1);
//        } catch (MyException e){
//            System.out.println(e.getMessage());
//        }

//        public FriendsResponse request(Long friendId){
//            User user = getAuthenticatedUser();
//            User friend = userRepository.findById(friendId)
//                    .stream()
//                    .filter(us->user!=friend).filter(user1 -> friend.getRequestToFriends().contains())
//
//
//
//        }

    public FriendsResponse requestToFriend(Long friendId){
        User user = getAuthenticatedUser();
        User friend = userRepository.findById(friendId).get();
      if(friend == user){
          log.info("You can't send a request to yourself");
          throw new NotFoundException("You can't send a request to yourself");
      } else if (friend.getRequestToFriends().contains(user)) {
          log.info("Request already sent");
          throw new NotFoundException("Request already sent");
      } else if (friend.getFriends().contains(user)) {
          log.info("This user is already in your friends");
          throw  new RuntimeException("This user is already in your friends");
      }

          friend.addRequestToFriend(user);
          log.info("Request to friend successfully send");
        friend.addNotification(sendNotification(user,friendId));
        notificationRepository.saveAll(friend.getNotifications());
      return mapToFriendResponse(friend);
    }
    private Notification sendNotification(User user,Long friendId){
        Notification notification = new Notification();
        notification.setCreated(LocalDate.now());
        notification.setUser(user);
        notification.setReceiverId(friendId);
        return notification;
    }
    public FriendsResponse deleteFriend(Long friendId) {
            User user = getAuthenticatedUser();
            User friend = userRepository.findById(friendId).get();
            if (user.getFriends().contains(friend)) {
                friend.getFriends().remove(user);
                user.getFriends().remove(friend);
                log.info("Successfully deleted user with name : {}", friend.getFirstName());
            }else {
                log.error("You have not friend with id "+friend.getId());
                throw new NotFoundException("You have not friend with id "+friend.getId());
            }
            return mapToFriendResponse(friend);
        }



    private FriendsResponse mapToFriendResponse(User user){
        return FriendsResponse.builder()
                .id(user.getId())
                .image(user.getImage())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
    }
    private FriendsResponse mapToFriendProfileResponse(User user) {
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

