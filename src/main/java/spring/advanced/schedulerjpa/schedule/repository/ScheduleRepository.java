package spring.advanced.schedulerjpa.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.advanced.schedulerjpa.schedule.domain.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
