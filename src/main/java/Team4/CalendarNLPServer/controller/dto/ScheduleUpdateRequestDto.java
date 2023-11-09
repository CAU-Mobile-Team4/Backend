package Team4.CalendarNLPServer.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ScheduleUpdateRequestDto {

    private String event;
    private String location;
    private String month;
    private String day;
    private String time;

    @Builder
    public ScheduleUpdateRequestDto(String event, String location, String month, String day, String time) {
        this.event = event;
        this.location = location;
        this.month = month;
        this.day = day;
        this.time = time;
    }

}
