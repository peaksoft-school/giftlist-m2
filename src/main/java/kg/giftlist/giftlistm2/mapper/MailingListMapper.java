package kg.giftlist.giftlistm2.mapper;

import kg.giftlist.giftlistm2.controller.payload.request.MailingListRequest;
import kg.giftlist.giftlistm2.controller.payload.response.MailingListResponse;
import kg.giftlist.giftlistm2.db.entity.MailingList;
import kg.giftlist.giftlistm2.exception.EmptyValueException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class MailingListMapper {

    public MailingList mapToEntity(MailingListRequest request){
        return MailingList.builder().header(request.getHeader())
                .image(request.getImage())
                .text(request.getText())
                .createdAt(LocalDate.now())
                .build();
    }

    public MailingListResponse mapToResponse(MailingList mailingList){
        if (mailingList == null){
            throw new EmptyValueException("Mailing lists are empty");
        }
       return MailingListResponse.builder()
                .id(mailingList.getId())
                .header(mailingList.getHeader())
                .image(mailingList.getImage())
                .text(mailingList.getText())
                .createdAt(LocalDate.now())
                .build();
    }

}
