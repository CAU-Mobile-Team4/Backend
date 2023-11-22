package Team4.CalendarNLPServer.domain.student;

import Team4.CalendarNLPServer.domain.schedule.Schedule;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    @Column
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

    public void deleteSchedule(Schedule schedule) {
        this.schedules.remove(schedule);
    }

}
