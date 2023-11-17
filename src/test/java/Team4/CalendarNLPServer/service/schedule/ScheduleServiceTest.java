package Team4.CalendarNLPServer.service.schedule;

import Team4.CalendarNLPServer.controller.dto.StudentResponseDto;
import Team4.CalendarNLPServer.service.student.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

//@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
class ScheduleServiceTest {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ScheduleService scheduleService;

    @BeforeEach
    public void deleteAll() {
        scheduleService.deleteAll();
        studentService.deleteAll();
    }

//    @Test
//    public void serviceTest() throws Exception {
//
//        String text1 = "11월 13일 오전 10시에 강남역에서 회의가 있어.";
//        String text2 = "10월 15일에 집에서 건담 조립 할거야.";
//        String text3 = "1월 1일에 중앙대학교를 갈거야.";
//        String text4 = "12월 20일 오후 7시 시험";
//        String text5 = "12월 25일 크리스마스";
//
//        StudentResponseDto responseDto1= StudentResponseDto.builder()
//                .id(20194566L)
//                .name("김동현")
//                .build();
//        studentService.join(responseDto1);
//        StudentResponseDto responseDto2= StudentResponseDto.builder()
//                .id(20194386L)
//                .name("이승재")
//                .build();
//        studentService.join(responseDto2);
//        StudentResponseDto responseDto3= StudentResponseDto.builder()
//                .id(20196123L)
//                .name("박상혁")
//                .build();
//        studentService.join(responseDto3);
//
//        long student_id1 = responseDto1.getId();
//        long student_id2 = responseDto2.getId();
//        long student_id3 = responseDto3.getId();
//        scheduleService.save(student_id1, text1);
//        scheduleService.save(student_id2, text2);
//        scheduleService.save(student_id3, text3);
//        scheduleService.save(student_id2, text4);
//        scheduleService.save(student_id1, text5);
//    }

    @Test
    public void 일정_저장() {

    }

}