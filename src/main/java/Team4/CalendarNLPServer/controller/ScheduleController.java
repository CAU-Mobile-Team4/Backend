package Team4.CalendarNLPServer.controller;

import Team4.CalendarNLPServer.service.schedule.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/schedule/{id}")
    public Long save(@PathVariable Long id,
                     @RequestBody String text) throws Exception {
        return scheduleService.save(id, text);
    }

//    @PostMapping("/schedule")
//    public
//    TODO: 모든 일정 반환 컨트롤러 findAll()
}
