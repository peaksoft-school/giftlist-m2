package kg.giftlist.giftlistm2.db.repository;

import kg.giftlist.giftlistm2.db.entity.Holiday;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {

    @Query("select h from User u join u.holidays h where u.id=?1")
    List<Holiday> getAllUserHolidays(Long userId);

    @Query("select h from Holiday h where upper(h.name) like concat('%',:text,'%') ")
    List<Holiday> searchAndPagination(@Param("text") String text, Pageable pageable);

}
