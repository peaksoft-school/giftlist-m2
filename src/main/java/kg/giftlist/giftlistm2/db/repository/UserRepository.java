package kg.giftlist.giftlistm2.db.repository;

import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    @Query("select f from User u join u.friends f where u.id=?1 ")
    List<User> getAllFriendByUserId(Long id);

    @Query("select f from User u join u.friends f where  f.id=?1")
    User getFriendById(Long id);

    @Query("select f from User u join u.requestToFriends f where u.id=?1")
    List<User> getAllRequestToFriend(Long id);

    @Query("select h from User u join u.wishLists h where u.id=?1")
    List<WishList> getAllUserWishList(Long userId);

    @Query("select u from User u where u.role = 'USER' ")
    List<User> getAll();

}