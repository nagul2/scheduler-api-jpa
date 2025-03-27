package spring.advanced.schedulerjpa.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.advanced.schedulerjpa.schedule.domain.dto.ScheduleCommonResponseDto;
import spring.advanced.schedulerjpa.schedule.domain.entity.Schedule;
import spring.advanced.schedulerjpa.schedule.repository.ScheduleRepository;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Override
    public ScheduleCommonResponseDto saveSchedule(String writer, String title, String content) {
        Schedule schedule = Schedule.builder()
                .writer(writer)
                .title(title)
                .content(content)
                .build();

        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleCommonResponseDto(savedSchedule.getId());
    }
}
