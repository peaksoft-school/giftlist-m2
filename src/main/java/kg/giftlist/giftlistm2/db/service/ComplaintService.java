package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.controller.payload.CharityComplaintResponse;
import kg.giftlist.giftlistm2.controller.payload.WishlistComplaintResponse;
import kg.giftlist.giftlistm2.controller.payload.ComplaintRequest;
import kg.giftlist.giftlistm2.db.entity.Charity;
import kg.giftlist.giftlistm2.db.entity.Complaints;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.entity.WishList;
import kg.giftlist.giftlistm2.db.repository.CharityRepository;
import kg.giftlist.giftlistm2.db.repository.ComplaintRepository;
import kg.giftlist.giftlistm2.db.repository.UserRepository;
import kg.giftlist.giftlistm2.db.repository.WishListRepository;
import kg.giftlist.giftlistm2.enums.ComplaintsType;
import kg.giftlist.giftlistm2.exception.BadCredentialsException;
import kg.giftlist.giftlistm2.exception.EmptyValueException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ComplaintService {

    private final UserRepository userRepository;
    private final WishListRepository wishListRepository;
    private final ComplaintRepository complaintRepository;
    private final CharityRepository charityRepository;
    private final EntityManager entityManager;

    public String createWishlistComplaint(Long id, ComplaintRequest request) {
        User user = getAuthenticatedUser();
        if (wishListRepository.findById(id).isEmpty()) {
            throw new EmptyValueException("There is no any wish list with id " + id);
        } else {
            WishList wishList = wishListRepository.findById(id).get();
            if (user.getWishLists().contains(wishList)) {
                throw new BadCredentialsException("You can not complain to your own posts!");
            } else {
                Complaints wishlistId = complaintRepository.getWishlistById(wishList.getId());
                if (user.getComplaints().contains(wishlistId)) {
                    System.out.println("UUUUUU");
                    throw new BadCredentialsException("You have complained this post");
                } else {
                    Complaints complaints = new Complaints();
                    if (request.getComplaints().isEmpty()) {
                        throw new EmptyValueException("The field must not be empty!");
                    }
                    complaints.setUser(user);
                    complaints.setComplaintsType(ComplaintsType.valueOf(request.getComplaints()));
                    complaints.setWishListId(wishList);
                    complaintRepository.save(complaints);
                    return "Thank you for letting us know!\n" +
                            "Your feedback helps us make the GIFT LIST community a safe environment for everyone.";
                }
            }
        }
    }

    public String createCharityComplaint(Long id, ComplaintRequest request) {
        User user = getAuthenticatedUser();
        if (charityRepository.findById(id).isEmpty()) {
            throw new EmptyValueException("There is no any charity with id " + id);
        } else {
            Charity charity = charityRepository.findById(id).get();
            if (user.getCharities().contains(charity)) {
                throw new BadCredentialsException("You can not complain to your own posts!");
            }
//                Complaints chary = complaintRepository.getCharityById(charity.getId());
            if (request.getComplaints().isEmpty()) {
                throw new EmptyValueException("The field must not be empty!");
            }
            Complaints complaints1 = complaintRepository.getCharityById(charity.getId());
            if (user.getComplaints().contains(complaints1)) {
                throw new BadCredentialsException("You have complained this post");
            } else {
                Complaints complaints = new Complaints();
                complaints.setUser(user);
                complaints.setComplaintsType(ComplaintsType.valueOf(request.getComplaints()));
                complaints.setCharityId(charity);
                complaintRepository.save(complaints);
                return "Thank you for letting us know!\n" +
                        "Your feedback helps us make the GIFT LIST community a safe environment for everyone.";

            }
        }
    }


    public WishlistComplaintResponse getWishlistComplaintById(Long id) {
        if (complaintRepository.findById(id).isEmpty()) {
            throw new EmptyValueException("There is no any complained wish list with id " + id);
        } else {
            Complaints complaints = complaintRepository.findById(id).get();
            return wishlistMapToResponse(complaints);
        }
    }

    public CharityComplaintResponse getCharityComplaintById(Long id) {
        if (complaintRepository.findById(id).isEmpty()) {
            throw new EmptyValueException("There is no any complained charity with id " + id);
        } else {
            Complaints complaints = complaintRepository.findById(id).get();
            return charityMapToResponse(complaints);
        }
    }

    public List<WishlistComplaintResponse> getAllWishListComplaints() {
        if (complaintRepository.findAll().isEmpty()) {
            throw new EmptyValueException("There is no any complained wish list");
        } else {
            List<Complaints> complaints = complaintRepository.getAllWishlistComplaints();
            return wishlistView(complaints);
        }
    }

    public List<CharityComplaintResponse> getAllCharityComplaints() {
        if (complaintRepository.findAll().isEmpty()) {
            throw new EmptyValueException("There is no any complained charity");
        }
        List<Complaints> complaints = complaintRepository.getAllCharityComplaints();
        return charityView(complaints);
    }

    public String deleteComplaint(Long id) {
        if (complaintRepository.findById(id).isEmpty()) {
            throw new EmptyValueException("There is no any complaint with id " + id);
        }
        Complaints complaints = complaintRepository.findById(id).get();
        log.info("complaint" + complaints.getComplaintsType());
        complaintRepository.deleteById(complaints.getId());
        return "Complaint successfully was deleted!";
    }

    public String blockCharity(Long id) {
        if (charityRepository.findById(id).isEmpty()) {
            throw new EmptyValueException("There is no any charity with id " + id);
        } else {
            Charity charity = charityRepository.findById(id).get();
            if (charity.isBlocked()) {
                throw new BadCredentialsException("You have already blocked the charity with id " + id);
            } else {
                charity.setBlocked(true);
                charityRepository.save(charity);
                return "You have blocked the charity with id " + id;
            }
        }
    }

    public String blockWishlist(Long id) {
        if (wishListRepository.findById(id).isEmpty()) {
            throw new EmptyValueException("There is no any wih list with id " + id);
        } else {
            WishList wishList = wishListRepository.findById(id).get();
            if (wishList.isBlocked()) {
                throw new BadCredentialsException("You have already blocked the wish list with id " + id);
            } else {
                wishList.setBlocked(true);
                wishListRepository.save(wishList);
                return "You have blocked the wish list with id " + id;
            }
        }
    }

    public String unBlockCharity(Long id) {
        if (charityRepository.findById(id).isEmpty()) {
            throw new EmptyValueException("There is no any charity with id " + id);
        } else {
            Charity charity = charityRepository.findById(id).get();
            if (!charity.isBlocked()) {
                throw new BadCredentialsException("You have already unblocked the charity with id " + id);
            } else {
                charity.setBlocked(false);
                charityRepository.save(charity);
                return "You have unblocked the charity with id " + id;
            }
        }
    }

    public String unBlockWishlist(Long id) {
        if (wishListRepository.findById(id).isEmpty()) {
            throw new EmptyValueException("There is no any wih list with id " + id);
        } else {
            WishList wishList = wishListRepository.findById(id).get();
            if (!wishList.isBlocked()) {
                throw new BadCredentialsException("You have already unblocked the wish list with id " + id);
            } else {
                wishList.setBlocked(false);
                wishListRepository.save(wishList);
                return "You have unblocked the wish list with id " + id;
            }
        }
    }


    private List<WishlistComplaintResponse> wishlistView(List<Complaints> complaints) {
        List<WishlistComplaintResponse> responses = new ArrayList<>();
        for (Complaints complaints1 : complaints) {
            responses.add(wishlistMapToResponse(complaints1));
        }
        return responses;
    }

    private List<CharityComplaintResponse> charityView(List<Complaints> complaints) {
        List<CharityComplaintResponse> responses = new ArrayList<>();
        for (Complaints complaints1 : complaints) {
            responses.add(charityMapToResponse(complaints1));
        }
        return responses;
    }

    private WishlistComplaintResponse wishlistMapToResponse(Complaints complaints) {
        if (complaints == null) {
            return null;
        }
        WishlistComplaintResponse response = new WishlistComplaintResponse();
        response.setId(complaints.getId());
        response.setWishlistId(complaints.getWishListId().getId());
        response.setGiftName(complaints.getWishListId().getGiftName());
        response.setUserId(complaints.getWishListId().getUser().getId());
        response.setFirstName(complaints.getWishListId().getUser().getFirstName());
        response.setLastName(complaints.getWishListId().getUser().getLastName());
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

    private CharityComplaintResponse charityMapToResponse(Complaints complaints) {
        if (complaints == null) {
            return null;
        }
        CharityComplaintResponse response = new CharityComplaintResponse();
        response.setId(complaints.getId());
        response.setCharityId(complaints.getCharityId().getId());
        response.setGiftName(complaints.getCharityId().getGiftName());
        response.setUserId(complaints.getCharityId().getUser().getId());
        response.setFirstName(complaints.getCharityId().getUser().getFirstName());
        response.setLastName(complaints.getCharityId().getUser().getLastName());
        response.setCharityStatus(complaints.getCharityId().getCharityStatus());
        response.setCondition(complaints.getCharityId().getCondition());
        response.setCategory(complaints.getCharityId().getCategory().getCategoryName());
        response.setImage(complaints.getCharityId().getImage());
        response.setDescription(complaints.getCharityId().getDescription());
        response.setCreatedDate(complaints.getCharityId().getCreatedDate());
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
