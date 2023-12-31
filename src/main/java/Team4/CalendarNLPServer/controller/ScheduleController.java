package Team4.CalendarNLPServer.controller;


import Team4.CalendarNLPServer.controller.dto.ScheduleListResponseDto;
import Team4.CalendarNLPServer.controller.dto.ScheduleSaveRequestDto;
import Team4.CalendarNLPServer.controller.dto.ScheduleTextResponseDto;
import Team4.CalendarNLPServer.controller.dto.ScheduleUpdateRequestDto;
import Team4.CalendarNLPServer.service.schedule.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 사용자 입력으로 stuId 학생의 일정 목록에 저장
    @PostMapping("/schedule/{stuId}")
    public Long save(@PathVariable Long stuId,
                     @RequestBody ScheduleSaveRequestDto requestDto) {
        return scheduleService.save(stuId, requestDto);
    }

    // NLP 기능으로 stuId 학생의 일정 목록에 저장
    @PostMapping("/schedule/nlp/{stuId}")
    public Long saveText(@PathVariable Long stuId,
                         @RequestBody ScheduleTextResponseDto responseDto) throws Exception {
        ScheduleSaveRequestDto requestDto = scheduleService.parsing(responseDto.getText());
        return scheduleService.save(stuId, requestDto);
    }

    // schId 일정의 수정
    @PutMapping("/schedule/{schId}")
    public Long update(@PathVariable Long schId,
                       @RequestBody ScheduleUpdateRequestDto requestDto) {
        return scheduleService.update(schId, requestDto);
    }

    // 비로그인 사용자 위한 모든 학교 일정 반환
    @GetMapping("/schedule")
    public List<ScheduleListResponseDto> findALlBeforeLogin() {
        return scheduleService.findAllScheduleBeforeLogin();
    }

    // stuId 학생의 모든 일정 List 반환
    @GetMapping("/schedule/{stuId}")
    public List<ScheduleListResponseDto> finaAll(@PathVariable Long stuId) {
        return scheduleService.findAllByStuID(stuId);
    }

    // stuId 학생의 일정 중에 keyword가 포함되어 있는 일정 List 반환
    @PostMapping("/schedule/search/{stuId}")
    public List<ScheduleListResponseDto> search(@PathVariable Long stuId,
                                                @RequestParam("keyword") String keyword) {
        return scheduleService.findAllByKeyword(stuId, keyword);
    }

    // stuId 학생의 schId 일정 삭제
    @DeleteMapping("/schedule/{stuId}/{schId}")
    public void delete(@PathVariable Long stuId,
                       @PathVariable Long schId) {
        scheduleService.delete(stuId, schId);
    }

}
