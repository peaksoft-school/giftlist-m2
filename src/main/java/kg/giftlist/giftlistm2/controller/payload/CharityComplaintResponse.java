package kg.giftlist.giftlistm2.controller.payload;

import kg.giftlist.giftlistm2.enums.CharityStatus;
import kg.giftlist.giftlistm2.enums.Condition;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class CharityComplaintResponse {

    private Long id;
    private Long charityId;
    private String giftName;
    private Long userId;
    private String firstName;
    private String lastName;
    private CharityStatus charityStatus;
    private Condition condition;
    private String category;
    private String subcategory;
    private String image;
    private String description;
    private LocalDate createdDate;
    private String complainingUserName;
    private String complainingUserLastname;
    private String complaintCause;

}
