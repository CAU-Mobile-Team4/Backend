package Team4.CalendarNLPServer.controller.dto;

import lombok.Getter;

@Getter
public class ScheduleTextResponseDto {

    private String text;

    public ScheduleTextResponseDto(String text) {
        this.text = text;
    }

}
