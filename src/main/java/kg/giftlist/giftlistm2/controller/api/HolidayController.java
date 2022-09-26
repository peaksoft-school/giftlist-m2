package kg.giftlist.giftlistm2.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.giftlist.giftlistm2.controller.payload.HolidayRequest;
import kg.giftlist.giftlistm2.controller.payload.HolidayResponse;
import kg.giftlist.giftlistm2.controller.payload.HolidayResponseView;
import kg.giftlist.giftlistm2.db.service.HolidayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/holiday")
@CrossOrigin
public class HolidayController {

    private final HolidayService service;

    @PostMapping
    public HolidayResponse create(@RequestBody HolidayRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public HolidayResponse update(@PathVariable Long id, @RequestBody HolidayRequest request) {
        return service.update(id, request);
    }

    @GetMapping("/{id}")
    public HolidayResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        return service.deleteById(id);
    }

    @GetMapping("getAll")
    public List<HolidayResponse> getHolidays() {
        return service.getHolidays();
    }

    @GetMapping
    public HolidayResponseView getAll(@RequestParam (name = "text",required = false) String text ,
                                      @RequestParam int page,
                                      @RequestParam int size){
        return service.getAllStudentPagination(text, page, size);
    }
}
