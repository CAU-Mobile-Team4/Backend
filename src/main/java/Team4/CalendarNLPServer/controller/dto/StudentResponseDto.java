package Team4.CalendarNLPServer.controller.dto;

import Team4.CalendarNLPServer.domain.student.Student;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class StudentResponseDto {

    private Long id;
    private String name;

    @Builder
    public StudentResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Student toEntity() {
        return Student.builder()
                .id(id)
                .name(name)
                .build();
    }

}
