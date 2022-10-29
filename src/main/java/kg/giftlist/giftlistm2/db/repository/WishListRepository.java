package kg.giftlist.giftlistm2.db.repository;

import kg.giftlist.giftlistm2.db.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishList, Long> {

}
