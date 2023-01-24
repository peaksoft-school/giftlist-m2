package kg.giftlist.giftlistm2.db.repository;

import kg.giftlist.giftlistm2.db.entity.ShoeSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoeRepository extends JpaRepository<ShoeSize, Long> {

    @Query("SELECT s FROM ShoeSize s WHERE s.size=38")
    List<ShoeSize> getDefaultSize();

}
