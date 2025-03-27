package spring.advanced.schedulerjpa.schedule.service;

import spring.advanced.schedulerjpa.schedule.domain.dto.ScheduleCommonResponseDto;
import spring.advanced.schedulerjpa.schedule.domain.dto.ScheduleFindResponseDto;

import java.util.List;

public interface ScheduleService {
    ScheduleCommonResponseDto saveSchedule(String writer, String title, String content);

    List<ScheduleFindResponseDto> findAllSchedules();

    ScheduleFindResponseDto findScheduleById(Long id);
}
