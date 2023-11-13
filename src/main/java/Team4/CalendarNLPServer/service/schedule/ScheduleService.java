package Team4.CalendarNLPServer.service.schedule;

import Team4.CalendarNLPServer.common.ScheduleAlreadyExistException;
import Team4.CalendarNLPServer.common.ScheduleNOTExistException;
import Team4.CalendarNLPServer.common.StudentNOTExistException;
import Team4.CalendarNLPServer.controller.dto.ScheduleListRequestDto;
import Team4.CalendarNLPServer.controller.dto.ScheduleSaveRequestDto;
import Team4.CalendarNLPServer.controller.dto.ScheduleUpdateRequestDto;
import Team4.CalendarNLPServer.domain.schedule.Schedule;
import Team4.CalendarNLPServer.domain.schedule.ScheduleRepository;
import Team4.CalendarNLPServer.domain.student.Student;
import Team4.CalendarNLPServer.domain.student.StudentRepository;
import Team4.CalendarNLPServer.service.NLP.DataNLP;
import com.google.cloud.language.v1beta2.Entity;
import com.google.cloud.language.v1beta2.EntityMention;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ScheduleService {

    private final DataNLP dataNLP;
    private final StudentRepository studentRepository;
    private final ScheduleRepository scheduleRepository;

    public ScheduleSaveRequestDto parsing(String text) throws Exception {
        List<Entity> data = dataNLP.createJSON(text);

        String event = null;
        String location = null;
        String month = null;
        String day = null;
        String time = null;
        for (Entity entity : data) {
            for (EntityMention mention : entity.getMentionsList()) {
                if (entity.getType().toString().equals("EVENT")) {
                    event = mention.getText().getContent();
                }
                if (entity.getType().toString().equals("LOCATION")) {
                    location = mention.getText().getContent();
                }
                if (entity.getType().toString().equals("DATE")) {
                    String clock = mention.getText().getContent();
                    if (entity.getMetadataMap().containsKey("month")) {
                        month = entity.getMetadataMap().get("month");
                        time = clock.replaceAll(month + "월", "");
                    }
                    if (entity.getMetadataMap().containsKey("day")) {
                        day = entity.getMetadataMap().get("day");
                        time = clock.replaceAll(day + "일", "");
                    }
                    time = clock.trim();
                }
            }
        }

        return ScheduleSaveRequestDto.builder()
                .event(event)
                .location(location)
                .month(month)
                .day(day)
                .time(time)
                .build();
    }

    @Transactional
    public Long save(Long id, String text) throws Exception {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNOTExistException("학생이 존재하지 않습니다"));

        ScheduleSaveRequestDto requestDto = parsing(text);
        findDuplicateSchedule(requestDto);

        requestDto.setStudent(student);
        Schedule schedule = requestDto.toEntity();
        scheduleRepository.save(schedule);
        student.addSchedule(schedule);

        return schedule.getId();
    }

    public void findDuplicateSchedule(ScheduleSaveRequestDto requestDto) {
        Optional<Schedule> schedule = scheduleRepository.findByEventAndMonthAndDayAndTime(requestDto.getEvent(), requestDto.getMonth(), requestDto.getDay(), requestDto.getTime());
        if (schedule.isPresent()) {
            throw new ScheduleAlreadyExistException("같은 일정이 존재합니다.");
        }
    }

    @Transactional
    public Long update(Long id, ScheduleUpdateRequestDto requestDto) {
        findScheduleById(id);
        Optional<Schedule> find = scheduleRepository.findById(id);
        Schedule schedule = find.get();
        schedule.update(requestDto.getEvent(), requestDto.getLocation(), requestDto.getMonth(), requestDto.getDay(), requestDto.getTime());

        return schedule.getId();
    }

    public void findScheduleById(Long id) {
        Optional<Schedule> schedule = scheduleRepository.findById(id);
        if (schedule.isEmpty()) {
            throw new ScheduleNOTExistException("일정이 없습니다.");
        }
    }

    @org.springframework.transaction.annotation.Transactional
    public List<ScheduleListRequestDto> findAllByStuID(Long id) {
        return scheduleRepository.findAllByUserIdDesc(id).stream()
                .map(ScheduleListRequestDto::new)
                .collect(Collectors.toList());
    }

    @org.springframework.transaction.annotation.Transactional
    public List<Schedule> findAllByKeyword(String keyword) {
        return scheduleRepository.findSchedulesByEventContains(keyword);
    }

    public void delete(Long stuId,Long schId) {
        Student student = studentRepository.findById(stuId)
                .orElseThrow(() -> new StudentNOTExistException("학생이 존재하지 않습니다."));
        Schedule schedule = scheduleRepository.findById(schId)
                .orElseThrow(() -> new ScheduleNOTExistException("일정이 존재하지 않습니다."));

        student.deleteSchedule(schedule);
        scheduleRepository.delete(schedule);
    }

}
