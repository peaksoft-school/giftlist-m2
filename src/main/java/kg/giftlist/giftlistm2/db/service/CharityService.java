package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.controller.payload.CharityRequest;
import kg.giftlist.giftlistm2.controller.payload.CharityResponse;
import kg.giftlist.giftlistm2.db.entity.Charity;

import java.util.List;

public interface CharityService {

    Charity getCharityById(Long id);

    List<Charity> getAllCharities();

    CharityResponse createCharity(CharityRequest request);

    CharityResponse updateCharity(Long id, CharityRequest request);

    void deleteCharity(Long id);

    List<CharityResponse> getCharityByUserId(Long userId);

}
