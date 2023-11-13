package Team4.CalendarNLPServer.domain.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    Optional<Schedule> findByEventAndMonthAndDayAndTime(String event, String month, String day, String time);

    @Query("SELECT s FROM Schedule s WHERE s.student.id = :id ORDER BY s.id DESC")
    List<Schedule> findAllByUserIdDesc(@Param("id") Long id);

    @Query("SELECT s FROM Schedule s WHERE s.event LIKE %:keyword% ORDER BY s.id DESC")
    List<Schedule> findSchedulesByEventContains(@Param("keyword") String keyword);


}
