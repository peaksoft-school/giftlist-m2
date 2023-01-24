package kg.giftlist.giftlistm2.db.repository;

import kg.giftlist.giftlistm2.db.entity.Complaints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaints, Long> {

    @Query("SELECT c FROM Complaints c WHERE c.wishList IS NOT NULL")
    List<Complaints> getAllWishlistComplaints();

    @Query("SELECT c FROM Complaints c WHERE c.charity IS NOT NULL")
    List<Complaints> getAllCharityComplaints();

}
