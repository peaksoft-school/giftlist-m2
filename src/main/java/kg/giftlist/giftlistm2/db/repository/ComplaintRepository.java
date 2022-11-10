package kg.giftlist.giftlistm2.db.repository;

import kg.giftlist.giftlistm2.db.entity.Complaints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaints, Long> {



//    @Query("select c from Complaints c where c.wishList is not null")
//    List<Complaints> getAllWishlistComplaints();

    @Query("select c from Complaints c where c.charity is not null")
    List<Complaints> getAllCharityComplaints();

    @Query("select c from Complaints c join User u on c.user.id=u.id where u.id=?1")
    Complaints getUserFromComplain(Long id);

    @Query("select distinct c from Complaints c join Charity ch on c.charity.id=ch.id where ch.id=?1")
    Complaints getCharityFromComplaint(Long id);



//    @Query("select c from Complaints c join User u on c.user.id=u.id where u.id=?1")
//    Complaints getWishlistFromComplaints(Long wishlistId);

}
