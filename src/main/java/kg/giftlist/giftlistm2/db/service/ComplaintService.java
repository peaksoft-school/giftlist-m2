package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.controller.payload.CharityComplaintResponse;
import kg.giftlist.giftlistm2.controller.payload.WishlistComplaintResponse;
import kg.giftlist.giftlistm2.controller.payload.ComplaintRequest;
import kg.giftlist.giftlistm2.db.entity.Charity;
import kg.giftlist.giftlistm2.db.entity.Complaints;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.entity.WishList;
import kg.giftlist.giftlistm2.db.repository.*;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class ComplaintService {

    private final UserRepository userRepository;
    private final WishListRepository wishListRepository;
    private final ComplaintRepository complaintRepository;
    private final CharityRepository charityRepository;
    private final EntityManager entityManager;
    private final ComplainRepImpl complainRep;

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
                    complaints.setWishList(wishList);
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
            Complaints getCharityFromComplaint = complaintRepository.getCharityById(charity.getId());
            Complaints get = complainRep.getCharityFromComp(charity.getId()).get();
            if (user.getComplaints().contains(get)) {
                throw new BadCredentialsException("You have complained this post");
            } else {
                Complaints complaints = new Complaints();
                complaints.setUser(user);
                complaints.setComplaintsType(ComplaintsType.valueOf(request.getComplaints()));
                complaints.setCharity(charity);
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
        response.setWishlistId(complaints.getWishList().getId());
        response.setGiftName(complaints.getWishList().getGiftName());
        response.setUserId(complaints.getWishList().getUser().getId());
        response.setFirstName(complaints.getWishList().getUser().getFirstName());
        response.setLastName(complaints.getWishList().getUser().getLastName());
        response.setLink(complaints.getWishList().getLink());
        response.setHolidayName(complaints.getWishList().getHolidays().getName());
        response.setHolidayDate(complaints.getWishList().getHolidayDate());
        response.setDescription(complaints.getWishList().getDescription());
        response.setImage(complaints.getWishList().getImage());
        response.setWishListStatus(complaints.getWishList().getWishListStatus());
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
        response.setCharityId(complaints.getCharity().getId());
        response.setGiftName(complaints.getCharity().getGiftName());
        response.setUserId(complaints.getCharity().getUser().getId());
        response.setFirstName(complaints.getCharity().getUser().getFirstName());
        response.setLastName(complaints.getCharity().getUser().getLastName());
        response.setCharityStatus(complaints.getCharity().getCharityStatus());
        response.setCondition(complaints.getCharity().getCondition());
        response.setCategory(complaints.getCharity().getCategory().getCategoryName());
        response.setImage(complaints.getCharity().getImage());
        response.setDescription(complaints.getCharity().getDescription());
        response.setCreatedDate(complaints.getCharity().getCreatedDate());
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
