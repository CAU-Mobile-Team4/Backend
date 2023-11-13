package Team4.CalendarNLPServer.controller;

import Team4.CalendarNLPServer.controller.dto.ScheduleListRequestDto;
import Team4.CalendarNLPServer.domain.schedule.Schedule;
import Team4.CalendarNLPServer.service.schedule.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/schedule/{id}")
    public Long save(@PathVariable Long id,
                     @RequestBody String text) throws Exception {
        return scheduleService.save(id, text);
    }

    @GetMapping("/schedule/{id}")
    public List<ScheduleListRequestDto> finaAll(@PathVariable Long id) {
        return scheduleService.findAllByStuID(id);
    }

    @PostMapping("/schedule/search/{id}")
    public List<Schedule> search(@PathVariable Long id,
                                 @RequestBody String keyword) {
        return scheduleService.findAllByKeyword(keyword);
    }

    @DeleteMapping("/schedule/{stuId}/{schId}")
    public void delete(@PathVariable Long stuId,
                       @PathVariable Long schId) {
        scheduleService.delete(stuId, schId);
    }

}
