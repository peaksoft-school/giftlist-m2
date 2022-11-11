package kg.giftlist.giftlistm2.db.repository;

import kg.giftlist.giftlistm2.db.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {

    @Query("select w from WishList w join User u on w.user.id=u.id where u.id=?1")
    List<WishList> getWishListByUserId(Long id);

    @Query("select w from User u join u.friends f join f.wishLists w where w.isBlock=false and u.id=?1 order by w.created DESC")
    List<WishList> getAllFriendWishes(Long userId);

    @Query("select aw from WishList aw where aw.isBlock=false order by aw.created DESC")
    List<WishList> getAllWishes();



}
