package kg.giftlist.giftlistm2.db.repository;

import kg.giftlist.giftlistm2.db.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b WHERE b.charity IS NOT NULL AND b.userId.id=?1")
    List<Booking> getAllCharityBooking(Long userId);

    @Query("SELECT b FROM Booking b WHERE b.wishList IS NOT NULL AND b.userId.id=?1")
    List<Booking> getAllWishListBooking(Long userId);

}
