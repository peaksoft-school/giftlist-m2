package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.db.entity.ResetPasswordToken;
import kg.giftlist.giftlistm2.db.repository.ResetPasswordTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResetPasswordTokenServiceImpl {

    private final ResetPasswordTokenRepository resetPasswordTokenRepository;

    public ResetPasswordToken findByToken(String token) {
        return resetPasswordTokenRepository.findByToken(token);
    }

    public ResetPasswordToken save(ResetPasswordToken resetPasswordToken) {
        return resetPasswordTokenRepository.save(resetPasswordToken);
    }

}
