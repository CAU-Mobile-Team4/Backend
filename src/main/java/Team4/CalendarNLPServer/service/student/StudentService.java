package Team4.CalendarNLPServer.service.student;

import Team4.CalendarNLPServer.common.StudentAlreadyExistException;
import Team4.CalendarNLPServer.controller.dto.StudentSaveRequestDto;
import Team4.CalendarNLPServer.domain.student.Student;
import Team4.CalendarNLPServer.domain.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public Long join(StudentSaveRequestDto requestDto) {
        findDuplicateStudent(requestDto);
        Student student=Student.builder()
                .id(requestDto.getId())
                .name(requestDto.getName())
                .build();
        studentRepository.save(student);

        return student.getId();
    }

    public void findDuplicateStudent(StudentSaveRequestDto requestDto) {
        Optional<Student> student = studentRepository.findById(requestDto.getId());
        if (student.isPresent()) {
            throw new StudentAlreadyExistException("이미 존재하는 학생입니다.");
        }
    }

}
