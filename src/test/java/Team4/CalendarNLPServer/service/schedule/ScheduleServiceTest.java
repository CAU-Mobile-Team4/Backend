package Team4.CalendarNLPServer.service.schedule;

import Team4.CalendarNLPServer.controller.dto.StudentResponseDto;
import Team4.CalendarNLPServer.service.student.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class ScheduleServiceTest {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ScheduleService scheduleService;

    @Test
    public void serviceTest() throws Exception {

        String text1 = "11월 13일 오전 10시에 강남역에서 회의가 있어.";
        String text2 = "15일에 우리 집에서 파티해";

        StudentResponseDto responseDto= StudentResponseDto.builder()
                .id(20194566L)
                .name("김동현")
                .build();
        studentService.join(responseDto);

        long student_id = responseDto.getId();
        scheduleService.save(student_id, text1);
        scheduleService.save(student_id, text2);
    }

}