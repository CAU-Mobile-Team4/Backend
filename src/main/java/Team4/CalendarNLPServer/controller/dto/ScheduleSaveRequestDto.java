package Team4.CalendarNLPServer.controller.dto;

import Team4.CalendarNLPServer.domain.schedule.Schedule;
import Team4.CalendarNLPServer.domain.student.Student;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class ScheduleSaveRequestDto {

    private String event;
    private String location;
    private String year;
    private String month;
    private String day;
    private String time;
    private Student student;

    @Builder
    public ScheduleSaveRequestDto(String event, String location, String year, String month, String day, String time) {
        this.event = event;
        this.location = location;
        this.year = year;
        this.month = month;
        this.day = day;
        this.time = time;
    }

    @Builder
    public ScheduleSaveRequestDto(ScheduleSaveRequestDto requestDto, Student student) {
        this.event = requestDto.getEvent();
        this.location = requestDto.getLocation();
        this.year = requestDto.getYear();
        this.month = requestDto.getMonth();
        this.day = requestDto.getDay();
        this.time = requestDto.getTime();
        this.student = student;
    }

    public Schedule toEntity() {
        return Schedule.builder()
                .event(event)
                .location(location)
                .year(year)
                .month(month)
                .day(day)
                .time(time)
                .student(student)
                .build();
    }

    public void setStudent(Student student) {
        this.student = student;
    }

}
