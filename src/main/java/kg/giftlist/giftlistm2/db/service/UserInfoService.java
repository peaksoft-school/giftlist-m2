package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.controller.payload.request.UserInfoRequest;
import kg.giftlist.giftlistm2.controller.payload.response.UserInfoResponse;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.repository.UserRepository;
import kg.giftlist.giftlistm2.exception.EmptyValueException;
import kg.giftlist.giftlistm2.mapper.UserInfoEditMapper;
import kg.giftlist.giftlistm2.mapper.UserInfoViewMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserInfoService {

    private final UserInfoEditMapper userInfoEditMapper;
    private final UserInfoViewMapper userInfoViewMapper;
    private final UserRepository userRepository;

    @Transactional
    public UserInfoResponse update(Long userInfoId, UserInfoRequest userInfoRequest) {
        if (userRepository.findById(userInfoId).isEmpty()) {
            log.error("There is no any user with id " + userInfoId);
            throw new EmptyValueException("There is no any user with id " + userInfoId);
        }
        User user = getAuthenticatedUser();
        String currentUserFirstName = user.getFirstName();
        String newUserFirstName = userInfoRequest.getFirstName();
        if (!currentUserFirstName.equals(newUserFirstName)) {
            user.setFirstName(newUserFirstName);
        }
        String currentUserLastName = user.getLastName();
        String newUserLastName = userInfoRequest.getLastName();
        if (!currentUserLastName.equals(newUserLastName)) {
            user.setLastName(newUserLastName);
        }
        user.setLastName(user.getLastName());
        String currentEmail = user.getEmail();
        String newEmail = userInfoRequest.getEmail();
        if (!currentEmail.equals(newEmail)) {
            user.setImage(newEmail);
        }

        User user1 = findByUserInfoId(userInfoId);
        userInfoEditMapper.update(user1, userInfoRequest);
        log.info("User info with id: {} successfully updated in db", user1.getId());
        return userInfoViewMapper.viewUserInfo(userRepository.save(user1));
    }

    public UserInfoResponse findById() {
        User user1 = getAuthenticatedUser();
        return userInfoViewMapper.viewUserInfo(user1);
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        log.info("User: " + authentication.getName());
        return userRepository.findByEmail(login);
    }

    public User findByUserInfoId(Long userInfoId) {
        return userRepository.findById(userInfoId).orElseThrow(() ->
                new NotFoundException(String.format("userInfo with id = %s does not exists", userInfoId)));
    }

}
