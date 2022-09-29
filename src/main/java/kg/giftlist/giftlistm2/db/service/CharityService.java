package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.controller.payload.CharityRequest;
import kg.giftlist.giftlistm2.controller.payload.CharityResponse;
import kg.giftlist.giftlistm2.db.entity.Charity;
import kg.giftlist.giftlistm2.db.repository.CharityRepository;
import kg.giftlist.giftlistm2.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CharityService {

    private final CharityRepository charityRepository;
    private final UserRepository userRepository;


    public Charity getCharityById(Long id) {
        return charityRepository.findById(id).get();
    }


    public List<Charity> getAllCharities() {
        return charityRepository.findAll();
    }


    public CharityResponse createCharity(CharityRequest request) {
        Charity charity = new Charity();
        charity.setGiftName (request.getGiftName());
        charity.setUser(userRepository.findById(request.getUserId()).get());
        charity.setCondition(request.getCondition());
        charity.setCategory(request.getCategory().getClass().cast(toString()));
        charity.setImage(request.getImage());
        charity.setDescription(request.getDescription());
        charity.setCreatedDate(LocalDate.now());
        charityRepository.save(charity);
        return mapToResponse(charity);
    }

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

    public void deleteCharity(Long id) {
        charityRepository.deleteById(id);
    }

    public List<CharityResponse> getCharityByUserId(Long userId) {
        List<Charity> charities = charityRepository.getCharityByUserId(userId);
        List<CharityResponse> responses = new ArrayList<>();
        for (Charity charity : charities) {
            responses.add(mapToResponse(charity));
        }
        return responses;
    }

    private CharityResponse mapToResponse(Charity charity) {
        if (charity == null) {
            return null;
        }
        CharityResponse charityResponse = new CharityResponse();
        charityResponse.setId(charity.getId());
        charityResponse.setGiftName(charity.getGiftName());
        charityResponse.setUser(charity.getUser());
        charityResponse.setCondition(charity.getCondition());
        charityResponse.setCategory(charity.getCategory().getClass().cast(toString()));
        charityResponse.setImage(charity.getImage());
        charityResponse.setDescription(charity.getDescription());
        charityResponse.setCreatedDate(LocalDate.now());
        return charityResponse;
    }

}
