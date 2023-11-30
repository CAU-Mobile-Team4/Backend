package Team4.CalendarNLPServer.controller;

import Team4.CalendarNLPServer.controller.dto.StudentResponseDto;
import Team4.CalendarNLPServer.service.student.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class StudentController {

    private final StudentService studentService;

    // 학생 회원가입
    @PostMapping("/join")
    public Long join(@RequestBody StudentResponseDto responseDto) {
        return studentService.join(responseDto);
    }

    // 학생 로그인
    @PostMapping("/login")
    public Long login(@RequestBody StudentResponseDto responseDto) {
        return studentService.login(responseDto);
    }

}
