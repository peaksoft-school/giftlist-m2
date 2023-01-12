package kg.giftlist.giftlistm2.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.giftlist.giftlistm2.controller.payload.request.MailingListRequest;
import kg.giftlist.giftlistm2.controller.payload.response.MailingListResponse;
import kg.giftlist.giftlistm2.db.service.MailingListService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/mailing-list")
@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin(origins = "*", maxAge = 3600)
@SecurityRequirement(name = "Authorization")
@Tag(name = "Mailing list Api", description = "The Admin can send mailing list to users")
public class MailingListApi {

    private final MailingListService mailingListService;

    @Operation(summary = "Send mailing list", description = "Admin cans send mailing to all users")
    @PostMapping
    public MailingListResponse sendAndSave(@RequestBody MailingListRequest request) {
        return mailingListService.send(request);
    }

    @Operation(summary = "Get all mailing lists", description = "Admin cans get all mailing list")
    @GetMapping
    public List<MailingListResponse> getAllMailingList() {
        return mailingListService.getAllMailingList();
    }

    @Operation(summary = "Get mailing list", description = "Admin cans get mailing list by id")
    @GetMapping("{id}")
    public MailingListResponse getMailingListById(@PathVariable Long id) {
        return mailingListService.getMailingListById(id);
    }

}
