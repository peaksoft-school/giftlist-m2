package kg.giftlist.giftlistm2.db.repository;

import kg.giftlist.giftlistm2.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    @Query("select u from User u join u.friends f where f.id=?1 ")
    List<User> getAllFriendBYUserId(Long id);



}