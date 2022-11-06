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

    @Query("select w from WishList w where w.giftName like concat('%', :name, '%')or w.description like concat('%', :name, '%')")
    List<WishList> searchWishListByName(String name);
}
