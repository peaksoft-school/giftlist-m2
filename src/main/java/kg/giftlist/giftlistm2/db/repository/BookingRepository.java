package kg.giftlist.giftlistm2.db.repository;

import kg.giftlist.giftlistm2.db.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("select b from Booking b where b.charity is not null")
    List<Booking> getAllCharityBooking();

    @Query("select w from User b join Booking w  where b.id=?1")
    List<Booking> getWishListBookingByUserId(Long userId);

//    @Query("select c from Complaints c where c.wishList is not null")
//    List<Complaints> getAllWishlistComplaints();
//
//    @Query("select c from Complaints c where c.charity is not null")
//    List<Complaints> getAllCharityComplaints();

}
