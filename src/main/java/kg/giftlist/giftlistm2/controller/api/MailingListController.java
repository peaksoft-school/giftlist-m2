package kg.giftlist.giftlistm2.controller.api;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.giftlist.giftlistm2.controller.payload.MailingListRequest;
import kg.giftlist.giftlistm2.controller.payload.MailingListResponse;
import kg.giftlist.giftlistm2.db.service.MailingListService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/mailing-list")
@CrossOrigin
@RequiredArgsConstructor
@Tag(name = "User API", description = "The user can send a request, accept a request, unfriend, list all friends and all friend requests, and can see a friend's profile")
@SecurityRequirement(name = "Authorization")
@PreAuthorize("hasAuthority('ADMIN')")
public class MailingListController {

    private final MailingListService mailingListService;

    @PostMapping
    public MailingListResponse sendAndSave(@RequestBody MailingListRequest request){
        return mailingListService.send(request);
    }
}
