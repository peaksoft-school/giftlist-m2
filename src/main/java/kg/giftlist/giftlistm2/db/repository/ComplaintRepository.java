package kg.giftlist.giftlistm2.db.repository;

import kg.giftlist.giftlistm2.db.entity.Complaints;
import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaints, Long> {

    @Query("select c from Complaints c where c.wishListId is not null")
    List<Complaints> getAllWishlistComplaints();

    @Query("select c from Complaints c where c.charityId is not null")
    List<Complaints> getAllCharityComplaints();

}
