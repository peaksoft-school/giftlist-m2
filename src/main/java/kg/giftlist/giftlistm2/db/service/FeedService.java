package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.controller.payload.WishListResponse;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.entity.WishList;
import kg.giftlist.giftlistm2.db.repository.UserRepository;
import kg.giftlist.giftlistm2.db.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class FeedService {

    private final UserRepository userRepository;
    private final WishListService wishListService;
    private final WishListRepository wishListRepository;

    public List<WishListResponse> getWishesForFeed() {
        User user = getAuthenticatedUser();
        List<WishList> allFriendWishes = wishListRepository.getAllFriendWishes(user.getId());
        List<WishList> allWishes = wishListRepository.getAllWishes();
        List<WishList> sortedWishes  = new ArrayList<>(allFriendWishes);
        allWishes.removeAll(allFriendWishes);
        sortedWishes.addAll(allWishes);
        return wishListService.view(sortedWishes);
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login);
    }

}
