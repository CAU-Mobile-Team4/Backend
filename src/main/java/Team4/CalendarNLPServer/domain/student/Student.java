package Team4.CalendarNLPServer.domain.student;

import Team4.CalendarNLPServer.domain.schedule.Schedule;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Student {

    @Id
    private Long id;        // 학번

    @Column(nullable = false)
    private String name;    // 이름

    @OneToMany(mappedBy = "student")
    private List<Schedule> schedules = new ArrayList<>();

    @Builder
    public Student(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addSchedule(Schedule schedule) {
        this.schedules.add(schedule);
    }

}
