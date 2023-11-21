package Team4.CalendarNLPServer.service.schedule;

import Team4.CalendarNLPServer.exceptions.ScheduleAlreadyExistException;
import Team4.CalendarNLPServer.exceptions.ScheduleNOTExistException;
import Team4.CalendarNLPServer.exceptions.StudentNOTExistException;
import Team4.CalendarNLPServer.controller.dto.ScheduleListResponseDto;
import Team4.CalendarNLPServer.controller.dto.ScheduleSaveRequestDto;
import Team4.CalendarNLPServer.controller.dto.ScheduleUpdateRequestDto;
import Team4.CalendarNLPServer.domain.schedule.Schedule;
import Team4.CalendarNLPServer.domain.schedule.ScheduleRepository;
import Team4.CalendarNLPServer.domain.student.Student;
import Team4.CalendarNLPServer.domain.student.StudentRepository;
import Team4.CalendarNLPServer.service.NLP.DataNLP;
import com.google.cloud.language.v1beta2.Entity;
import com.google.cloud.language.v1beta2.EntityMention;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                if (entity.getType().toString().equals("EVENT") || entity.getType().toString().equals("ORGANIZATION") || entity.getType().toString().equals("OTHER")) {
                    event = mention.getText().getContent();
                }
                if (entity.getType().toString().equals("LOCATION")) {
                    location = mention.getText().getContent();
                }
                if (entity.getType().toString().equals("DATE")) {
                    time = mention.getText().getContent();
                    if (entity.getMetadataMap().containsKey("month")) {
                        month = entity.getMetadataMap().get("month");
                        time = time.replaceFirst(month + "월", "");
                    }
                    if (entity.getMetadataMap().containsKey("day")) {
                        day = entity.getMetadataMap().get("day");
                        time = time.replaceFirst(day + "일", "");
                    }
                    if (time.equals(" ")) {
                        time = null;
                    } else {
                        time = time.trim();
                    }
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
    public Long save(Long stuId, ScheduleSaveRequestDto requestDto) {
        Student student = studentRepository.findById(stuId)
                .orElseThrow(() -> new StudentNOTExistException("학생이 존재하지 않습니다"));

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
    public Long update(Long schId, ScheduleUpdateRequestDto requestDto) {
        findScheduleById(schId);
        Optional<Schedule> find = scheduleRepository.findById(schId);
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

    @Transactional
    public List<ScheduleListResponseDto> findAllByStuID(Long stuId) {
        return scheduleRepository.findAllByUserIdDesc(stuId).stream()
                .map(ScheduleListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ScheduleListResponseDto> findAllByKeyword(Long stuId, String keyword) {
        return scheduleRepository.findSchedulesByEventContains(stuId, keyword).stream()
                .map(ScheduleListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long stuId, Long schId) {
        Student student = studentRepository.findById(stuId)
                .orElseThrow(() -> new StudentNOTExistException("학생이 존재하지 않습니다."));
        Schedule schedule = scheduleRepository.findById(schId)
                .orElseThrow(() -> new ScheduleNOTExistException("일정이 존재하지 않습니다."));

        student.deleteSchedule(schedule);
        scheduleRepository.delete(schedule);
    }

    public void deleteAll() {
        scheduleRepository.deleteAll();
    }

}
