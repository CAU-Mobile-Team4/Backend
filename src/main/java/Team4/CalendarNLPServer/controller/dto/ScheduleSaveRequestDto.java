package Team4.CalendarNLPServer.controller.dto;

import Team4.CalendarNLPServer.domain.schedule.Schedule;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ScheduleSaveRequestDto {

    private String event;
    private String location;
    private String month;
    private String day;
    private String time;

    @Builder
    public ScheduleSaveRequestDto(String event, String location, String month, String day, String time) {
        this.event = event;
        this.location = location;
        this.month = month;
        this.day = day;
        this.time = time;
    }

    public Schedule toEntity() {
        return Schedule.builder()
                .event(event)
                .location(location)
                .month(month)
                .day(day)
                .time(time)
                .build();
    }

}
