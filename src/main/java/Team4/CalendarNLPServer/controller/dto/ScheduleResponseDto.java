package Team4.CalendarNLPServer.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ScheduleResponseDto {

    private Long id;
    private String event;
    private String location;
    private String year;
    private String month;
    private String day;
    private String time;

    @Builder
    public ScheduleResponseDto(Long id, String event, String location, String year, String month, String day, String time) {
        this.id = id;
        this.event = event;
        this.location = location;
        this.year = year;
        this.month = month;
        this.day = day;
        this.time = time;
    }

}
