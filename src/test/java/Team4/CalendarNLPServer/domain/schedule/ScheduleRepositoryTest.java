package Team4.CalendarNLPServer.domain.schedule;

import Team4.CalendarNLPServer.domain.student.Student;
import Team4.CalendarNLPServer.domain.student.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class ScheduleRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Test
    public void testSave() {
        Student student = Student.builder()
                .id(20194566L)
                .name("김동현")
                .build();
        studentRepository.save(student);

        Schedule schedule = Schedule.builder()
                .event("모바일 앱 개발")
                .location("중앙대학교")
                .month("11")
                .day("12")
                .time("17:00")
                .student(student)
                .build();
        scheduleRepository.save(schedule);
        student.addSchedule(schedule);

        // Schedule 객체 조회
        Schedule found = scheduleRepository.findById(schedule.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getEvent()).isEqualTo("모바일 앱 개발");
        assertThat(found.getStudent().getId()).isEqualTo(20194566L);
    }

}