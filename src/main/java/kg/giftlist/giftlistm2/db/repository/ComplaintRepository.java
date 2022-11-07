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

    @Query("select c from Complaints c join WishList w on c.wishList.id=w.id where w.id=?1")
    Complaints getWishlistById(Long id);

    @Query("select c from Complaints c where c.charity is not null")
    List<Complaints> getAllCharityComplaints();

    @Query("select c from Complaints c where c.charity.id=?1")
    Complaints getCharityById(Long id);

    @Query("select c from Complaints c join User u on c.user.id=u.id join Charity ch on c.charity.id=ch.id where u.id=?1 and  ch.id=?2")
    Complaints findCharityByUser(Long userId, Long charityId);

}
