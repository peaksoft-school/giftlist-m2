package kg.giftlist.giftlistm2.db.repository;

import kg.giftlist.giftlistm2.db.entity.Booking;
import kg.giftlist.giftlistm2.db.entity.Charity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("select b from Booking b join Charity c on b.charity.id=c.id where c.id=?1")
    List<Charity> getBookingsByCharityId(Long id);

    @Query("select c from User u join u.bookings b join b.charity c c where u.id=?1")
    List<Charity> getWishListBookingByUserId(Long userId);

}
