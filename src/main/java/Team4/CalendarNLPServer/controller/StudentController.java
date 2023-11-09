package Team4.CalendarNLPServer.controller;

import Team4.CalendarNLPServer.controller.dto.StudentSaveRequestDto;
import Team4.CalendarNLPServer.service.student.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/student")
    public Long join(@RequestBody StudentSaveRequestDto requestDto) {
        return studentService.join(requestDto);
    }

}
