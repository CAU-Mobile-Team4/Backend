package Team4.CalendarNLPServer.controller;

import Team4.CalendarNLPServer.controller.dto.StudentResponseDto;
import Team4.CalendarNLPServer.service.student.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class StudentController {

    private final StudentService studentService;

    // 사용자 로그인 및 회원가입
    // 입력하였을 때 DB에 정보가 있으면 로그인, 정보가 없으면 자동 회원가입
    @PostMapping("/student")
    public Long join(@RequestBody StudentResponseDto responseDto) {
        return studentService.join(responseDto);
    }

}
