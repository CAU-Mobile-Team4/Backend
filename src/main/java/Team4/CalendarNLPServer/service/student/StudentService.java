package Team4.CalendarNLPServer.service.student;

import Team4.CalendarNLPServer.controller.dto.StudentResponseDto;
import Team4.CalendarNLPServer.domain.student.Student;
import Team4.CalendarNLPServer.domain.student.StudentRepository;
import Team4.CalendarNLPServer.exceptions.StudentAlreadyExistException;
import Team4.CalendarNLPServer.exceptions.StudentLoginFailureException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    // 회원가입
    @Transactional
    public Long join(StudentResponseDto responseDto) {
        findDuplicatedStudent(responseDto.getId());

        Student student = Student.builder()
                .id(responseDto.getId())
                .name(responseDto.getName())
                .build();
        studentRepository.save(student);

        return student.getId();
    }

    public void findDuplicatedStudent(Long stuId) {
        Optional<Student> student = studentRepository.findById(stuId);
        if (student.isPresent()) {
            throw new StudentAlreadyExistException("학생이 이미 존재합니다.");
        }
    }

    // 로그인
    @Transactional
    public Long login(StudentResponseDto responseDto) {
        Optional<Student> findStu = studentRepository.findById(responseDto.getId());
        if (findStu.isPresent()) {
            if (responseDto.getName().equals(findStu.get().getName())) {
                return findStu.get().getId();
            }
        }
        throw new StudentLoginFailureException("로그인에 실패했습니다.");
    }

    public void deleteAll() {
        studentRepository.deleteAll();
    }

}