package kg.giftlist.giftlistm2.db.service.impl;

import kg.giftlist.giftlistm2.controller.payload.CharityRequest;
import kg.giftlist.giftlistm2.controller.payload.CharityResponse;
import kg.giftlist.giftlistm2.db.entity.Charity;
import kg.giftlist.giftlistm2.db.repository.CharityRepository;
import kg.giftlist.giftlistm2.db.repository.UserRepository;
import kg.giftlist.giftlistm2.db.service.CharityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CharityServiceImpl implements CharityService {

    private final CharityRepository charityRepository;
    private final UserRepository userRepository;

    @Override
    public Charity getCharityById(Long id) {
        return charityRepository.findById(id).get();
    }

    @Override
    public List<Charity> getAllCharities() {
        return charityRepository.findAll();
    }

    @Override
    public CharityResponse createCharity(CharityRequest request) {
        Charity charity = new Charity();
        charity.setGiftName (request.getGiftName());
        charity.setUser(charity.getUser());
        charity.setCondition(request.getCondition());
        charity.setCategory(request.getCategory());
        charity.setImage(request.getImage());
        charity.setDescription(request.getDescription());
        charity.setCreatedDate(LocalDate.now());
        charityRepository.save(charity);
        return mapToResponse(charity);
    }

    @Override
    public CharityResponse updateCharity(Long id, CharityRequest request) {
        Charity charity = charityRepository.findById(id).get();
        charity.setGiftName(request.getGiftName());
        charity.setCondition(request.getCondition());
        charity.setCategory(request.getCategory());
        charity.setImage(request.getImage());
        charity.setDescription(request.getDescription());
        charity.setCreatedDate(LocalDate.now());
        charityRepository.save(charity);
        return mapToResponse(charity);
    }

    @Override
    public void deleteCharity(Long id) {
        charityRepository.deleteById(id);
    }

    @Override
    public List<CharityResponse> getCharityByUserId(Long userId) {
        List<Charity> charities = charityRepository.getCharityByUserId(userId);
        List<CharityResponse> responses = new ArrayList<>();
        for (Charity charity : charities) {
            responses.add(mapToResponse(charity));
        }
        return responses;
    }

//    @Override
//    public List<Charity> search(String name, Pageable pageable) {
//        String text = name == null ? "" : name;
//        List<Charity>
//        return null;
//    }
//
//    @Override
//    public List<CharityResponse> pagination(String text, int page, int size) {
//        return null;
//    }

//    @Override
//    public List<User> search(String name, Pageable pageable, LocalDate fromDate, LocalDate toDate) {
//        String text = name == null ? "" : name;
//        List<User> responses = userRepository.searchAndPagination("STUDENT", text, pageable);
//        List<User> users = new ArrayList<>();
//        for (User user : responses) {
//            if (!(user.getCreated().isAfter(toDate) || user.getCreated().isBefore(fromDate))) {
//                users.add(user);
//            }
//        }return users;
//    }
//
//    @Override
//    public List<StudentResponse> pagination(String text, int page, int size,LocalDate startDate, LocalDate endDate) {
//        List<StudentResponse> responses = new ArrayList<>();
//        Pageable pageable = PageRequest.of(page - 1, size);
//        List<User> users = search(text, pageable,startDate,endDate);
//        for (User user : users) {
//            responses.add(mapToStudentResponse(user));
//        }
//        return responses;
//    }

    private CharityResponse mapToResponse(Charity charity) {
        if (charity == null) {
            return null;
        }
        CharityResponse charityResponse = new CharityResponse();
        charityResponse.setId(charity.getId());
        charityResponse.setGiftName(charity.getGiftName());
        charityResponse.setUser(charity.getUser());
        charityResponse.setCondition(charity.getCondition());
        charityResponse.setCategory(charity.getCategory());
        charityResponse.setImage(charity.getImage());
        charityResponse.setDescription(charity.getDescription());
        charityResponse.setCreatedDate(LocalDate.now());
        return charityResponse;
    }

}
