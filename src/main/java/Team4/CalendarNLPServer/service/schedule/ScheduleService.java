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
        String year = "2023";
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
                    if (entity.getMetadataMap().containsKey("year")) {
                        month = entity.getMetadataMap().get("year");
                        time = time.replaceFirst(year + "년", "");
                    }
                    if (entity.getMetadataMap().containsKey("month")) {
                        month = entity.getMetadataMap().get("month");
                        time = time.replaceFirst(month + "월", "");
                        time = time.replaceFirst("January", "");
                        time = time.replaceFirst("Jan.", "");
                        time = time.replaceFirst("February", "");
                        time = time.replaceFirst("Feb.", "");
                        time = time.replaceFirst("March", "");
                        time = time.replaceFirst("Mar.", "");
                        time = time.replaceFirst("April", "");
                        time = time.replaceFirst("Apr.", "");
                        time = time.replaceFirst("May", "");
                        time = time.replaceFirst("May.", "");
                        time = time.replaceFirst("June", "");
                        time = time.replaceFirst("Jun.", "");
                        time = time.replaceFirst("July", "");
                        time = time.replaceFirst("Jul.", "");
                        time = time.replaceFirst("August", "");
                        time = time.replaceFirst("Aug.", "");
                        time = time.replaceFirst("September", "");
                        time = time.replaceFirst("Sep.", "");
                        time = time.replaceFirst("October", "");
                        time = time.replaceFirst("Oct.", "");
                        time = time.replaceFirst("November", "");
                        time = time.replaceFirst("Nov.", "");
                        time = time.replaceFirst("December", "");
                        time = time.replaceFirst("Dec.", "");
                    }
                    if (entity.getMetadataMap().containsKey("day")) {
                        day = entity.getMetadataMap().get("day");
                        time = time.replaceFirst(day + "일", "");
                    }
                    time = time.replaceFirst("at", "");
                    time = time.replaceFirst("am.", "");
                    time = time.replaceFirst("pm.", "");
                    time = time.replaceFirst("'o Clock", "");
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

        if (requestDto.getEvent() == null) {
            requestDto.setEvent("");
        }
        if (requestDto.getLocation() == null) {
            requestDto.setLocation("");
        }
        if (requestDto.getMonth() == null) {
            requestDto.setMonth("");
        }
        if (requestDto.getDay() == null) {
            requestDto.setDay("");
        }

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
        schedule.update(requestDto.getEvent(), requestDto.getLocation(), requestDto.getYear(), requestDto.getMonth(), requestDto.getDay(), requestDto.getTime());

        return schedule.getId();
    }

    public void findScheduleById(Long id) {
        Optional<Schedule> schedule = scheduleRepository.findById(id);
        if (schedule.isEmpty()) {
            throw new ScheduleNOTExistException("일정이 없습니다.");
        }
    }

    @Transactional
    public List<ScheduleListResponseDto> findAllScheduleBeforeLogin() {
        return scheduleRepository.findAllScheduleBeforeLogin().stream()
                .map(ScheduleListResponseDto::new)
                .collect(Collectors.toList());
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
