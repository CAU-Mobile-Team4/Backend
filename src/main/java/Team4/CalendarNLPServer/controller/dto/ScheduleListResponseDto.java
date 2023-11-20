package Team4.CalendarNLPServer.controller.dto;

import Team4.CalendarNLPServer.domain.schedule.Schedule;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScheduleListResponseDto {

    private Long id;
    private String event;
    private String location;
    private String month;
    private String day;
    private String time;

    public ScheduleListResponseDto(Schedule entity) {
        this.id = entity.getId();
        this.event = entity.getEvent();
        this.location = entity.getLocation();
        this.month = entity.getMonth();
        this.day = entity.getDay();
        this.time = entity.getTime();
    }

}
