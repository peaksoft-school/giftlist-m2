package kg.giftlist.giftlistm2.db.repository;

import kg.giftlist.giftlistm2.db.entity.MailingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MailingListRepository extends JpaRepository<MailingList, Long> {

}