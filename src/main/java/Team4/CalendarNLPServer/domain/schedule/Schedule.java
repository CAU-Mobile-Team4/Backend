package Team4.CalendarNLPServer.domain.schedule;

import Team4.CalendarNLPServer.domain.student.Student;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String event;

    @Column
    private String location;

    @Column(name = "when_month")
    private String month;

    @Column(nullable = false, name = "when_day")
    private String day;

    @Column(name = "when_time")
    private String time;

    @ManyToOne
    @JoinColumn(name = "Student_id")
    private Student student;

    @Builder
    public Schedule(String event, String location, String month, String day, String time, Student student) {
        this.event = event;
        this.location = location;
        this.month = month;
        this.day = day;
        this.time = time;
        this.student = student;
    }

    public void update(String event, String location, String month, String day, String time) {
        this.event = event;
        this.location = location;
        this.month = month;
        this.day = day;
        this.time = time;
    }

}
