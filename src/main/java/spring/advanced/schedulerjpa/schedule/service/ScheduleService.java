package spring.advanced.schedulerjpa.schedule.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import spring.advanced.schedulerjpa.schedule.domain.dto.ScheduleCommonResponseDto;
import spring.advanced.schedulerjpa.schedule.domain.dto.ScheduleFindAllPagingResponseDto;
import spring.advanced.schedulerjpa.schedule.domain.dto.ScheduleFindResponseDto;

public interface ScheduleService {
    ScheduleCommonResponseDto saveSchedule(Long userId, String title, String content);

    Page<ScheduleFindAllPagingResponseDto> findAllSchedules(Pageable pageable);

    ScheduleFindResponseDto findScheduleById(Long id);

    ScheduleCommonResponseDto updateSchedule(Long id, String title, String content);

    void deleteSchedule(Long id);

}
