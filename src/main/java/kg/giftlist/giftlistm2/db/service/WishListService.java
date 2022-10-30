package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.controller.payload.WishListRequest;
import kg.giftlist.giftlistm2.controller.payload.WishListResponse;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.entity.WishList;
import kg.giftlist.giftlistm2.db.repository.UserRepository;
import kg.giftlist.giftlistm2.db.repository.WishListRepository;
import kg.giftlist.giftlistm2.enums.WishListStatus;
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
    private final WishListRepository wishListRepository;

    public WishListResponse create(WishListRequest request) {
        User user = getAuthenticatedUser();
        WishList wishList = new WishList();
        wishList.setGiftName(request.getGiftName());
        wishList.setLink(request.getLink());
        wishList.setImage(request.getImage());
        wishList.setHolidays(request.getHolidays());
        wishList.setHolidayDate(request.getHolidayDate());
        wishList.setDescription(request.getDescription());
        wishList.setWishListStatus(WishListStatus.NOT_BOOKED);
        wishListRepository.save(wishList);
        return mapToResponse(wishList);
    }

    public WishListResponse update(Long id, WishListRequest request) {
        User user = getAuthenticatedUser();
        WishList wishList = wishListRepository.findById(id).get();
        wishList.setGiftName(request.getGiftName());
        wishList.setLink(request.getLink());
        wishList.setImage(request.getImage());
        wishList.setHolidays(request.getHolidays());
        wishList.setHolidayDate(request.getHolidayDate());
        wishList.setDescription(request.getDescription());
        wishList.setWishListStatus(WishListStatus.NOT_BOOKED);
        wishListRepository.save(wishList);
        return mapToResponse(wishList);
    }

    public String delete(Long id) {
        User user = getAuthenticatedUser();
        WishList wishList = wishListRepository.findById(id).get();
        wishListRepository.deleteById(wishList.getId());
        return "Wish list successfully was deleted!";
    }

    public WishListResponse getWishListById(Long id) {
        User user = getAuthenticatedUser();
        WishList wishList = wishListRepository.findById(id).get();
        return mapToResponse(wishList);
    }

    public List <WishListResponse> getAllWishLists() {
        User user = getAuthenticatedUser();
        List <WishList> wishLists = wishListRepository.getWishListByUserId(user.getId());
        return view(wishLists);
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
