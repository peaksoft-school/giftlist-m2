package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.controller.payload.request.ComplaintRequest;
import kg.giftlist.giftlistm2.controller.payload.response.CharityComplaintResponse;
import kg.giftlist.giftlistm2.controller.payload.response.WishlistComplaintResponse;
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

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ComplaintService {

    private final UserRepository userRepository;
    private final WishListRepository wishListRepository;
    private final ComplaintRepository complaintRepository;
    private final CharityRepository charityRepository;
    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;

    public String createWishlistComplaint(Long id, ComplaintRequest request) {
        User user = getAuthenticatedUser();
        if (wishListRepository.findById(id).isEmpty()) {
            log.error("There is no any wish list with id " + id);
            throw new EmptyValueException("There is no any wish list with id " + id);
        } else {
            WishList wishList = wishListRepository.findById(id).get();
            if (user.getWishLists().contains(wishList)) {
                log.error("You can not complain to your own posts!");
                throw new BadCredentialsException("You can not complain to your own posts!");
            }
            if (request.getComplaints().isEmpty()) {
                log.error("The field must not be empty!");
                throw new EmptyValueException("The field must not be empty!");
            }
            if (wishList.isBlocked()) {
                log.error("This wish list was already blocked");
                throw new BadCredentialsException("This wish list was already blocked");
            }
            List<Complaints> complaintsList = complaintRepository.getAllWishlistComplaints();
            for (Complaints allComplaints : complaintsList) {
                if (allComplaints.getUser().getId().equals(user.getId()) && allComplaints.getWishList().getId().equals(wishList.getId())) {
                    log.error("You have already complained this post!");
                    throw new BadCredentialsException("You have already complained this post!");
                }
            }
            Complaints complaints = new Complaints();
            complaints.setUser(user);
            complaints.setComplaintsType(ComplaintsType.valueOf(request.getComplaints()));
            complaints.setWishList(wishList);
            complaintRepository.save(complaints);
            User admin = userRepository.findByEmail("admin@gmail.com");
            complaints.addNotification(notificationService.sendWishlistComplaintNotification(user, new ArrayList<>(List.of(admin)), complaints));
            notificationRepository.saveAll(complaints.getNotifications());
            log.info("Thank you for letting us know!\n" +
                    "Your feedback helps us make the GIFT LIST community a safe environment for everyone.");
            return "Thank you for letting us know!\n" +
                    "Your feedback helps us make the GIFT LIST community a safe environment for everyone.";
        }
    }

    public String createCharityComplaint(Long id, ComplaintRequest request) {
        User user = getAuthenticatedUser();
        if (charityRepository.findById(id).isEmpty()) {
            log.error("There is no any charity with id " + id);
            throw new EmptyValueException("There is no any charity with id " + id);
        } else {
            Charity charity = charityRepository.findById(id).get();
            if (user.getCharities().contains(charity)) {
                log.error("You can not complain to your own posts!");
                throw new BadCredentialsException("You can not complain to your own posts!");
            }
            if (request.getComplaints().isEmpty()) {
                log.error("The field must not be empty!");
                throw new EmptyValueException("The field must not be empty!");
            }
            if (charity.isBlocked()) {
                log.error("This charity was already blocked");
                throw new BadCredentialsException("This charity was already blocked");
            }
            List<Complaints> complaintsList = complaintRepository.getAllCharityComplaints();
            for (Complaints allComplaints : complaintsList) {
                if (allComplaints.getUser().getId().equals(user.getId()) && allComplaints.getCharity().getId().equals(charity.getId())) {
                    log.error("You have already complained this post!");
                    throw new BadCredentialsException("You have already complained this post!");
                }
            }
            Complaints complaints = new Complaints();
            complaints.setUser(user);
            complaints.setComplaintsType(ComplaintsType.valueOf(request.getComplaints()));
            complaints.setCharity(charity);
            complaintRepository.save(complaints);
            User admin = userRepository.findByEmail("admin@gmail.com");
            complaints.addNotification(notificationService.sendCharityComplaintNotification(user, new ArrayList<>(List.of(admin)), complaints));
            notificationRepository.saveAll(complaints.getNotifications());
            log.info("Thank you for letting us know!\n" +
                    "Your feedback helps us make the GIFT LIST community a safe environment for everyone.");
            return "Thank you for letting us know!\n" +
                    "Your feedback helps us make the GIFT LIST community a safe environment for everyone.";
        }
    }

    public WishlistComplaintResponse getWishlistComplaintById(Long id) {
        if (complaintRepository.findById(id).isEmpty()) {
            log.error("There is no any complained wish list with id " + id);
            throw new EmptyValueException("There is no any complained wish list with id " + id);
        } else {
            Complaints complaints = complaintRepository.findById(id).get();
            log.info("Get wish list from the complaint list");
            return wishlistMapToResponse(complaints);
        }
    }

    public CharityComplaintResponse getCharityComplaintById(Long id) {
        if (complaintRepository.findById(id).isEmpty()) {
            log.error("There is no any complained charity with id " + id);
            throw new EmptyValueException("There is no any complained charity with id " + id);
        } else {
            Complaints complaints = complaintRepository.findById(id).get();
            log.info("Get charity from the complaint list");
            return charityMapToResponse(complaints);
        }
    }

    public List<WishlistComplaintResponse> getAllWishListComplaints() {
        List<Complaints> complaints = complaintRepository.getAllWishlistComplaints();
        log.info("Get all wish list from the complaint list");
        return wishlistView(complaints);
    }

    public List<CharityComplaintResponse> getAllCharityComplaints() {
        List<Complaints> complaints = complaintRepository.getAllCharityComplaints();
        log.info("Get all charity from the complaint list");
        return charityView(complaints);
    }

    public String deleteComplaint(Long id) {
        if (complaintRepository.findById(id).isEmpty()) {
            log.error("There is no any complaint with id " + id);
            throw new EmptyValueException("There is no any complaint with id " + id);
        }
        Complaints complaints = complaintRepository.findById(id).get();
        log.info("complaint" + complaints.getComplaintsType());
        complaintRepository.deleteById(complaints.getId());
        log.info("Complaint successfully was deleted!");
        return "Complaint successfully was deleted!";
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
        response.setWishListId(complaints.getWishList().getId());
        response.setGiftName(complaints.getWishList().getGiftName());
        response.setUserId(complaints.getWishList().getUser().getId());
        response.setFirstName(complaints.getWishList().getUser().getFirstName());
        response.setLastName(complaints.getWishList().getUser().getLastName());
        response.setLink(complaints.getWishList().getLink());
        response.setHolidayName(complaints.getWishList().getHolidays().getName());
        response.setCreatedAt(complaints.getWishList().getHolidayDate());
        response.setDescription(complaints.getWishList().getDescription());
        response.setImage(complaints.getWishList().getImage());
        response.setWishListStatus(complaints.getWishList().getWishListStatus());
        response.setComplainerFirstName(complaints.getUser().getFirstName());
        response.setComplainerLastName(complaints.getUser().getLastName());
        response.setComplaintReason(complaints.getComplaintsType().name());
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
        response.setCreatedAt(complaints.getCharity().getCreatedAt());
        response.setComplainerFirstName(complaints.getUser().getFirstName());
        response.setComplainerLastName(complaints.getUser().getLastName());
        response.setComplaintReason(complaints.getComplaintsType().name());
        return response;
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        log.info("User: " + authentication.getName());
        return userRepository.findByEmail(login);
    }

}
