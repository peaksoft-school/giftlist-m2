package kg.giftlist.giftlistm2.db.service.serviceImpl;

import kg.giftlist.giftlistm2.db.entity.ResetPasswordToken;
import kg.giftlist.giftlistm2.db.repository.ResetPasswordTokenRepository;
import kg.giftlist.giftlistm2.db.service.ResetPasswordTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResetPasswordTokenServiceImpl implements ResetPasswordTokenService {

    private final ResetPasswordTokenRepository resetPasswordTokenRepository;
    @Override
    public ResetPasswordToken findByToken(String token) {
        return resetPasswordTokenRepository.findByToken(token);
    }

    @Override
    public ResetPasswordToken save(ResetPasswordToken resetPasswordToken) {
        return resetPasswordTokenRepository.save(resetPasswordToken);
    }
}
