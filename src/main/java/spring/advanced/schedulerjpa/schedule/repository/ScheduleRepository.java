package spring.advanced.schedulerjpa.schedule.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import spring.advanced.schedulerjpa.schedule.domain.dto.ScheduleFindAllPagingResponseDto;
import spring.advanced.schedulerjpa.schedule.domain.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query(value = """ 
    select new spring.advanced.schedulerjpa.schedule.domain.dto.ScheduleFindAllPagingResponseDto(
        s.id,
        u.username,
        s.title,
        s.content,
        s.createAt,
        s.modifyAt,
        (select count(c) from Comment c where c.schedule.id = s.id)
        )
    from Schedule s
    join s.user u
    """,
    countQuery = "select count(s) from Schedule s")
    Page<ScheduleFindAllPagingResponseDto> findAllWithCommentCount(Pageable pageable);
}
