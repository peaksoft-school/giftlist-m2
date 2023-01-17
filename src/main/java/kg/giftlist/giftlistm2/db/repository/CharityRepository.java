package kg.giftlist.giftlistm2.db.repository;

import kg.giftlist.giftlistm2.db.entity.Charity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharityRepository extends JpaRepository<Charity, Long> {

    @Query("SELECT ch FROM Charity ch JOIN User u ON ch.user.id=u.id WHERE u.id=?1")
    List<Charity> getCharityByUserId(Long id);

    @Query("SELECT ch FROM User u JOIN u.charities ch WHERE ch.isBlocked=false AND u.id=?1")
    List<Charity> getCharities(Long userId);

}
