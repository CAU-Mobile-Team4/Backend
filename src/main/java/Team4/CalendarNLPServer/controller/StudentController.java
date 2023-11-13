package Team4.CalendarNLPServer.controller;

import Team4.CalendarNLPServer.controller.dto.StudentResponseDto;
import Team4.CalendarNLPServer.service.student.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/student")
    public Long join(@RequestBody StudentResponseDto responseDto) {
        return studentService.join(responseDto);
    }

}
