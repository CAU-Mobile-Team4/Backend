package Team4.CalendarNLPServer.domain.schedule;

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

    @Column
    private String month;

    @Column(nullable = false)
    private String day;

    @Column
    private String time;

    @Builder
    public Schedule(String event, String location, String month, String day, String time) {
        this.event = event;
        this.location = location;
        this.month = month;
        this.day = day;
        this.time = time;
    }

    public void update(String event, String location, String month, String day) {
        this.event = event;
        this.location = location;
        this.month = month;
        this.day = day;
        this.time = time;
    }

}
