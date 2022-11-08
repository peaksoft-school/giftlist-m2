package kg.giftlist.giftlistm2.db.repository;

import kg.giftlist.giftlistm2.db.entity.Complaints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaints, Long> {

    @Query("select c from Complaints c where c.wishList is not null")
    List<Complaints> getAllWishlistComplaints();

    @Query("select c from Complaints c where c.charity is not null")
    List<Complaints> getAllCharityComplaints();

    @Query("select c from Complaints c where c.user.id=?1")
    Complaints getUserFromComplain(Long userId);

    @Query("select c from Complaints c join User u on c.user.id=u.id where u.id=?1")
    Complaints getWishlistFromComplaints(Long wishlistId);

}
