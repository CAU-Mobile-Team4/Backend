package Team4.CalendarNLPServer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void 로그인_회원가입_controllerTest() throws Exception {

        // {"id":20190000, "name":"studentName"} 으로 JSON 요청
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("id", 20190000L);
        requestMap.put("name", "studentName");

        String content = new ObjectMapper().writeValueAsString(requestMap);

        mockMvc.perform(
                        post("/student")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                )
                .andDo(print())
                // HTTP 코드는 200 status().isOk()
                .andExpect(status().isOk())
                // 반환 값은 JSON 형태가 아닌 단일값 "20190000"
                .andExpect(content().string("20190000"));

    }

}