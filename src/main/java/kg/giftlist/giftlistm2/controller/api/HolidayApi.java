package kg.giftlist.giftlistm2.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.giftlist.giftlistm2.controller.payload.request.HolidayRequest;
import kg.giftlist.giftlistm2.controller.payload.response.HolidayResponse;
import kg.giftlist.giftlistm2.db.service.HolidayService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/holidays")
@PreAuthorize("hasAuthority('USER')")
@CrossOrigin(origins = "*", maxAge = 3600)
@SecurityRequirement(name = "Authorization")
@Tag(name = "Holiday API", description = "User with role \"User\"  can create, update or delete holidays")
public class HolidayApi {

    private final HolidayService service;

    @Operation(summary = "Create holiday", description = "User can create a holiday")
    @PostMapping
    public HolidayResponse create(@RequestBody HolidayRequest request) {
        return service.create(request);
    }

    @Operation(summary = "Update holiday", description = "User can update a holiday")
    @PutMapping("{id}")
    public HolidayResponse update(@PathVariable Long id, @RequestBody HolidayRequest request) {
        return service.update(id, request);
    }

    @Operation(summary = "Get holiday", description = "User can get holiday")
    @GetMapping("{id}")
    public HolidayResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @Operation(summary = "Delete holiday", description = "User can delete holiday")
    @DeleteMapping("{id}")
    public String delete(@PathVariable Long id) {
        return service.deleteById(id);
    }

    @Operation(summary = "Get all holidays", description = "User can get all holidays")
    @GetMapping
    public List<HolidayResponse> getHolidays() {
        return service.getHolidays();
    }

}
