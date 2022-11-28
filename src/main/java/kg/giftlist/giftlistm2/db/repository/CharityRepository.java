package kg.giftlist.giftlistm2.db.repository;

import kg.giftlist.giftlistm2.db.entity.Charity;
import kg.giftlist.giftlistm2.enums.CharityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharityRepository extends JpaRepository<Charity, Long> {

    @Query("select ch from Charity ch join User u on ch.user.id=u.id where u.id=?1")
    List<Charity> getCharityByUserId(Long id);

    @Query("select ch from User u join u.charities ch where ch.isBlocked=false and u.id=?1")
    List<Charity> getCharities(Long userId);

    @Query("select ch from Charity ch where (upper(ch.giftName) like upper(concat('%',:name,'%')) " +
            "or :name = 'all')and (ch.charityStatus = :status or :status is null ) " +
            "and (:categoryId is null or :categoryId = ch.category.id) " +
            "and (:subCategoryId is null or :subCategoryId = ch.subcategory.id) order by ch.createdAt desc ")
    List<Charity> filterGiftForAdmin(String name, CharityStatus status, Long categoryId, Long subCategoryId);


}
