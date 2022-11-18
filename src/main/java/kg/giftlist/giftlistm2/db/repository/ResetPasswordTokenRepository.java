package kg.giftlist.giftlistm2.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ResetPasswordTokenRepository extends JpaRepository<kg.giftlist.giftlistm2.db.entity.ResetPasswordToken, Long> {

    kg.giftlist.giftlistm2.db.entity.ResetPasswordToken findByToken(String token);
}