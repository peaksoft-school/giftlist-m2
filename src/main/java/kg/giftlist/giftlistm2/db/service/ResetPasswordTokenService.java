package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.db.entity.ResetPasswordToken;

public interface ResetPasswordTokenService {
    ResetPasswordToken findByToken(String token);
    ResetPasswordToken save(ResetPasswordToken resetPasswordToken);
}