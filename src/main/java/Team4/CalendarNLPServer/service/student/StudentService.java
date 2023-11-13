package Team4.CalendarNLPServer.service.student;

import Team4.CalendarNLPServer.controller.dto.StudentResponseDto;
import Team4.CalendarNLPServer.domain.student.Student;
import Team4.CalendarNLPServer.domain.student.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Transactional
    public Long join(StudentResponseDto responseDto) {
        Optional<Student> findStu = studentRepository.findById(responseDto.getId());
        if (findStu.isPresent()) {
            if (responseDto.getName().equals(findStu.get().getName())) {
                return findStu.get().getId();
            }
        }
        Student student = Student.builder()
                .id(responseDto.getId())
                .name(responseDto.getName())
                .build();
        studentRepository.save(student);

        return student.getId();
    }

}
