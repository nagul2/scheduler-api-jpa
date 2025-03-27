package spring.advanced.schedulerjpa.schedule.service;

import spring.advanced.schedulerjpa.schedule.domain.dto.ScheduleCommonResponseDto;

public interface ScheduleService {
    ScheduleCommonResponseDto saveSchedule(String writer, String title, String content);
}
