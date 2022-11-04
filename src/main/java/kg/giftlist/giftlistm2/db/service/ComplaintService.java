package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.controller.payload.ComplaintRequest;
import kg.giftlist.giftlistm2.db.entity.Complaints;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.entity.WishList;
import kg.giftlist.giftlistm2.db.repository.ComplaintRepository;
import kg.giftlist.giftlistm2.db.repository.UserRepository;
import kg.giftlist.giftlistm2.db.repository.WishListRepository;
import kg.giftlist.giftlistm2.enums.ComplaintsType;
import kg.giftlist.giftlistm2.exception.BadCredentialsException;
import kg.giftlist.giftlistm2.exception.EmptyValueException;
import kg.giftlist.giftlistm2.exception.WishListExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class ComplaintService {

    private final UserRepository userRepository;
    private final WishListRepository wishListRepository;
    private final ComplaintRepository complaintRepository;

    public String complaint(Long id, ComplaintRequest request) {
        User user = getAuthenticatedUser();
        Complaints complaints = new Complaints();
        WishList wishList = wishListRepository.findById(id).get();
        if (wishListRepository.existsById(id)) {
            if (request.getComplaints().isEmpty()) {
                throw new EmptyValueException("The field must not be empty!");
            }
            complaints.setUser(user);
            complaints.setComplaintsType(ComplaintsType.valueOf(request.getComplaints()));
            complaints.setWishListId(wishListRepository.getById(id));
            complaintRepository.save(complaints);
            return "Thank you for letting us know!\n" +
                    "Your feedback helps us make the GIFT LIST community a safe environment for everyone.";
        } else {
            throw new EmptyValueException("There is no any wish list with id " + id);
        }
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login);
    }

}
