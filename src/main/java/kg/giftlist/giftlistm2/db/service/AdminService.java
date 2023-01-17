package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.controller.payload.response.AdminPageUserGetAllResponse;
import kg.giftlist.giftlistm2.controller.payload.response.CommonUserProfileResponse;
import kg.giftlist.giftlistm2.controller.payload.response.SimpleResponse;
import kg.giftlist.giftlistm2.db.entity.Charity;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.entity.WishList;
import kg.giftlist.giftlistm2.db.repository.UserRepository;
import kg.giftlist.giftlistm2.enums.Role;
import kg.giftlist.giftlistm2.mapper.HolidayMapToResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@AllArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final WishListService wishListService;
    private final HolidayMapToResponse holidayMapToResponse;
    private final CharityService charityService;

    public List<AdminPageUserGetAllResponse> getAllUsers() {
        List<User> users = userRepository.getAll();
        List<AdminPageUserGetAllResponse> userList = new ArrayList<>();
        for (User i : users) {
            userList.add(mapToAdminPageUser(i));
        }
        log.info("Get all users");
        return userList;
    }

    @Transactional
    public SimpleResponse blockUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("user with id: %s not found", id)));
        user.setIsBlock(true);
        log.info("Successfully blocked user with id: {}", user.getId());
        return new SimpleResponse("BLOCK", "user with id: " + id + " blocked");
    }

    @Transactional
    public SimpleResponse unBlockUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("user with id: %s not found", id)));
        user.setIsBlock(false);
        log.info("Successfully unblocked user with id: {}", user.getId());
        return new SimpleResponse("UNBLOCK", "user with id:" + id + " unblocked");
    }

    public AdminPageUserGetAllResponse mapToAdminPageUser(User user) {
        if (user == null) {
            return null;
        }
        AdminPageUserGetAllResponse adminUserGetAllResponse = new AdminPageUserGetAllResponse();
        adminUserGetAllResponse.setId(user.getId());
        adminUserGetAllResponse.setGiftCount(user.getWishLists().size());
        adminUserGetAllResponse.setFirstName(user.getFirstName());
        adminUserGetAllResponse.setLastName(user.getLastName());
        adminUserGetAllResponse.setImage(user.getImage());
        adminUserGetAllResponse.setIsBlock(user.getIsBlock());

        return adminUserGetAllResponse;
    }

    public CommonUserProfileResponse getCommonFriendProfile(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("User with id:" + userId + " not found"));
        return viewCommonFriendProfile(user);
    }

    private CommonUserProfileResponse viewCommonFriendProfile(User user) {
        User authenticatedUser = getAuthenticatedUser();
        CommonUserProfileResponse commonUserProfileResponse = new CommonUserProfileResponse();
        commonUserProfileResponse.setId(user.getId());
        commonUserProfileResponse.setFirstName(user.getFirstName());
        commonUserProfileResponse.setLastName(user.getLastName());
        commonUserProfileResponse.setEmail(user.getEmail());
        commonUserProfileResponse.setImage(user.getImage());
        commonUserProfileResponse.setCity(user.getCity());
        commonUserProfileResponse.setDateOfBirth(user.getDateOfBirth());
        commonUserProfileResponse.setPhoneNumber(user.getPhoneNumber());
        commonUserProfileResponse.setClothingSize(user.getClothingSize());
        commonUserProfileResponse.setShoeSize(user.getShoeSize());
        commonUserProfileResponse.setHobby(user.getHobbies());
        commonUserProfileResponse.setImportantNote(user.getImportantToKnow());
        List<WishList> wishes = user.getWishLists();
        List<WishList> sortWishes = new ArrayList<>();
        for (WishList wish : wishes) {
            if (authenticatedUser.getRole().equals(Role.USER)) {
                if (wish.getIsBlock().equals(false)) {
                    sortWishes.add(wish);
                }
            } else if (authenticatedUser.getRole().equals(Role.ADMIN)) {
                sortWishes.add(wish);
            }
        }
        commonUserProfileResponse.setWishes(wishListService.view(sortWishes));
        commonUserProfileResponse.setHolidays(holidayMapToResponse.view(user.getHolidays()));

        List<Charity> gifts = user.getCharities();
        List<Charity> sortGifts = new ArrayList<>();
        for (Charity charity : gifts) {
            if (authenticatedUser.getRole().equals(Role.USER)) {
                if (charity.getIsBlock().equals(false)) {
                    sortGifts.add(charity);
                }
            } else if (authenticatedUser.getRole().equals(Role.ADMIN)) {
                sortGifts.add(charity);
            }
        }
        commonUserProfileResponse.setCharities(charityService.view(sortGifts));
        commonUserProfileResponse.setIsBlock(user.getIsBlock());
        return commonUserProfileResponse;
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        log.info("Admin: " + authentication.getName());
        return userRepository.findByEmail(login);
    }

}
