package kg.giftlist.giftlistm2.db.repository;

import kg.giftlist.giftlistm2.db.entity.Complaints;
import kg.giftlist.giftlistm2.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaints, Long> {

    @Query("select c from Complaints c where c.wishListId is not null")
    List<Complaints> getAllWishlistComplaints();

    @Query("select c from Complaints c join WishList w on c.wishListId.id=w.id where w.id=?1")
    Complaints getWishlistById(Long id);

    @Query("select c from Complaints c where c.charityId is not null")
    List<Complaints> getAllCharityComplaints();

    @Query("select c from Complaints c join Charity ch on c.charityId.id=ch.id where ch.id=?1")
    Complaints getCharityById(Long id);

}
