package kg.giftlist.giftlistm2.db.repository;

import kg.giftlist.giftlistm2.db.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WishListRepository extends JpaRepository<WishList, Long> {

    @Query("select w from WishList w join User u on w.user.id=u.id where u.id=?1")
    List<WishList> getWishListByUserId(Long id);

}
