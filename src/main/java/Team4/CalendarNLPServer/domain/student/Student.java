package Team4.CalendarNLPServer.domain.student;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Student {

    @Id
    private Long id;        // 학번

    @Column(nullable = false)
    private String name;    // 이름

    @Builder
    public Student(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
