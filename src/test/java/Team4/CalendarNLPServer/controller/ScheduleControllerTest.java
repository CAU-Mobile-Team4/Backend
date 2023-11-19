package Team4.CalendarNLPServer.controller;

import Team4.CalendarNLPServer.controller.dto.StudentResponseDto;
import Team4.CalendarNLPServer.service.schedule.ScheduleService;
import Team4.CalendarNLPServer.service.student.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class ScheduleControllerTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ScheduleService scheduleService;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

        StudentResponseDto student = StudentResponseDto.builder()
                .id(20190000L)
                .name("studentName")
                .build();
        studentService.join(student);
    }

    @AfterEach
    public void clean() {
        scheduleService.deleteAll();
        studentService.deleteAll();
    }

    @Test
    public void 일정_저장_controllerTest() throws Exception {

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("text", "11월 13일 오전 10시에 강남역에서 회의가 있어.");

        String content = new ObjectMapper().writeValueAsString(requestMap);

        mockMvc.perform(
                        post("/schedule/20190000")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

    }

}