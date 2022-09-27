package kg.giftlist.giftlistm2.db.service.impl;

import kg.giftlist.giftlistm2.controller.payload.CharityRequest;
import kg.giftlist.giftlistm2.controller.payload.CharityResponse;
import kg.giftlist.giftlistm2.db.entity.Charity;
import kg.giftlist.giftlistm2.db.repository.CharityRepository;
import kg.giftlist.giftlistm2.db.service.CharityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CharityServiceImpl implements CharityService {

    private final CharityRepository charityRepository;

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
        return null;
    }

    @Override
    public CharityResponse updateCharity(Long id, CharityRequest request) {
        return null;
    }

    @Override
    public void deleteCharity(Long id) {

    }

    @Override
    public List<CharityResponse> getCharityByUserId(Long userId) {
        return null;
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
        charityResponse.setCategory(charity.getCategory());
        charityResponse.setImage(charity.getImage());
        charityResponse.setDescription(charity.getDescription());
        charityResponse.setAddedDate(LocalDate.now());
        return charityResponse;
    }
}
