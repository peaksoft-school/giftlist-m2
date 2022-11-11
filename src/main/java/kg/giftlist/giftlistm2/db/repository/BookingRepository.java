package kg.giftlist.giftlistm2.db.repository;

import kg.giftlist.giftlistm2.db.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("select b from Booking b where b.charity is not null and b.userId.id=?1")
    List<Booking> getAllCharityBooking(Long userId);

    @Query("select b from Booking b  where b.wishList is not null and  b.userId.id=?1")
    List<Booking> getAllWishListBooking(Long userId);

    @Query("select b from Booking b  where b.wishList is not null")
    List<Booking> getAllWishListsBooking();

    @Query("select b from Booking b join WishList w on b.wishList.id=w.id where w.id=?1")
    Booking getWishlist(Long id);

}
