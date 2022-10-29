package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.controller.payload.WishListRequest;
import kg.giftlist.giftlistm2.controller.payload.WishListResponse;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.entity.WishList;
import kg.giftlist.giftlistm2.db.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WishListService {

    private final UserRepository userRepository;

    public WishListResponse create(WishListRequest request) {

    }

    public WishListResponse update(WishListRequest request) {

    }

    public String delete(Long id) {

    }

    private WishListResponse mapToResponse(WishList wishList) {
        if (wishList == null) {
            return null;
        }
        WishListResponse wishListResponse = new WishListResponse();
        wishListResponse.setId(wishList.getId());
        wishListResponse.setGiftName(wishList.getGiftName());
        wishListResponse.setLink(wishList.getLink());
        wishListResponse.setImage(wishList.getImage());
        wishListResponse.setHolidays(wishList.getHolidays());
        wishListResponse.setHolidayDate(wishList.getHolidayDate());
        wishListResponse.setDescription(wishList.getDescription());
        wishListResponse.setWishListStatus(wishList.getWishListStatus());
        return wishListResponse;
    }

    public List<WishListResponse> view(List<WishList> wishLists) {
        List<WishListResponse> responses = new ArrayList<>();
        for (WishList wishList : wishLists) {
            responses.add(mapToResponse(wishList));
        }
        return responses;
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login);
    }

}
