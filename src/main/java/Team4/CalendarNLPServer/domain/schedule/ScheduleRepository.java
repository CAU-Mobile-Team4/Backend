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

    @Query("SELECT s FROM Schedule s WHERE  s.student.id = 1 ORDER BY s.id DESC")
    List<Schedule> findAllScheduleBeforeLogin();

    @Query("SELECT s FROM Schedule s WHERE (s.student.id = :id OR s.student.id = 1) ORDER BY s.year, s.month, s.day ASC")
    List<Schedule> findAllByUserIdDesc(@Param("id") Long id);

    @Query("SELECT s FROM Schedule s WHERE (s.event LIKE %:keyword%) AND (s.student.id = :stuId OR s.student.id = 1) ORDER BY s.id DESC")
    List<Schedule> findSchedulesByEventContains(@Param("stuId") Long stuId, @Param("keyword") String keyword);

}
