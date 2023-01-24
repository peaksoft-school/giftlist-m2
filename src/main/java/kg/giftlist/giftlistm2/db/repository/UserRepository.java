package kg.giftlist.giftlistm2.db.repository;

import kg.giftlist.giftlistm2.db.entity.User;
import kg.giftlist.giftlistm2.db.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    @Query("SELECT case WHEN COUNT(u)>0 then true else false end FROM User u WHERE u.email LIKE:email")
    boolean getExistingEmail(@Param(value = "email") String email);

    @Query("SELECT f FROM User u JOIN u.friends f WHERE u.id=?1 ")
    List<User> getAllFriendByUserId(Long id);

    @Query("SELECT f FROM User u JOIN u.friends f WHERE  f.id=?1")
    User getFriendById(Long id);

    @Query("SELECT f FROM User u JOIN u.requestToFriends f WHERE u.id=?1")
    List<User> getAllRequestToFriend(Long id);

    @Query("SELECT h FROM User u JOIN u.wishLists h WHERE u.id=?1")
    List<WishList> getAllUserWishList(Long userId);

    @Query("SELECT u FROM User u WHERE u.role = 'USER' ")
    List<User> getAll();

}