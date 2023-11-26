package Team4.CalendarNLPServer.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScheduleTextResponseDto {

    private String text;

    public ScheduleTextResponseDto(String text) {
        this.text = text;
    }

}
