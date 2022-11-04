package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.controller.payload.ComplainedWishListResponse;
import kg.giftlist.giftlistm2.controller.payload.ComplaintRequest;
import kg.giftlist.giftlistm2.controller.payload.WishListResponse;
import kg.giftlist.giftlistm2.db.entity.Charity;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComplaintService {

    private final UserRepository userRepository;
    private final WishListRepository wishListRepository;
    private final ComplaintRepository complaintRepository;
    private final WishListService wishListService;

    public String complaint(Long id, ComplaintRequest request) {
        User user = getAuthenticatedUser();
        Complaints complaints = new Complaints();
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

    public ComplainedWishListResponse getById(Long id) {
        if (complaintRepository.findById(id).isEmpty()) {
            throw new EmptyValueException("There is no any complained wish list with id " + id);
        }
        Complaints complaints = complaintRepository.findById(id).get();
        return mapToResponse(complaints);
    }

    public List<ComplainedWishListResponse> getAllComplainedWishLists() {
        if (complaintRepository.findAll().isEmpty()) {
            throw new EmptyValueException("There is no any complained wish list");
        }
        List<Complaints> complaints = complaintRepository.findAll();
        return view(complaints);
    }

    public String delete(Long id) {
        return wishListService.delete(id);
    }

    private List<ComplainedWishListResponse> view(List<Complaints> complaints) {
        List<ComplainedWishListResponse> responses = new ArrayList<>();
        for (Complaints complaints1 : complaints) {
            responses.add(mapToResponse(complaints1));
        }
        return responses;
    }

    private ComplainedWishListResponse mapToResponse(Complaints complaints) {
        if (complaints == null) {
            return null;
        }
        ComplainedWishListResponse response = new ComplainedWishListResponse();
        response.setId(complaints.getWishListId().getId());
        response.setGiftName(complaints.getWishListId().getGiftName());
        response.setUserId(complaints.getUser().getId());
        response.setFirstName(complaints.getUser().getFirstName());
        response.setLastName(complaints.getUser().getLastName());
        response.setLink(complaints.getWishListId().getLink());
        response.setHolidayName(complaints.getWishListId().getHolidays().getName());
        response.setHolidayDate(complaints.getWishListId().getHolidayDate());
        response.setDescription(complaints.getWishListId().getDescription());
        response.setImage(complaints.getWishListId().getImage());
        response.setWishListStatus(complaints.getWishListId().getWishListStatus());
        response.setComplainingUserName(complaints.getUser().getFirstName());
        response.setComplainingUserLastname(complaints.getUser().getLastName());
        response.setComplaintCause(complaints.getComplaintsType().name());
        return response;
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        return userRepository.findByEmail(login);
    }

}
