package kg.giftlist.giftlistm2.db.repository;

import kg.giftlist.giftlistm2.db.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {

    @Query("SELECT w FROM WishList w JOIN User u ON w.user.id=u.id WHERE u.id=?1")
    List<WishList> getWishByUserId(Long id);

    @Query("SELECT w FROM User u JOIN u.friends f JOIN f.wishLists w WHERE w.isBlock=false AND u.id=?1 ORDER BY w.created DESC")
    List<WishList> getAllFriendWishes(Long userId);

    @Query("SELECT aw FROM WishList aw WHERE aw.isBlock=false ORDER BY aw.created DESC")
    List<WishList> getAllWishes();

    @Query("SELECT w FROM User u JOIN u.wishLists w WHERE w.isBlocked=false AND u.id=?1")
    List<WishList> getWishLists(Long userId);

}
