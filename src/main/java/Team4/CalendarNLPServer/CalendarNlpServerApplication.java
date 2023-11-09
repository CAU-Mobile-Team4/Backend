package Team4.CalendarNLPServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CalendarNlpServerApplication {

	public static void main(String[] args) {

		SpringApplication.run(CalendarNlpServerApplication.class, args);

	}

}
