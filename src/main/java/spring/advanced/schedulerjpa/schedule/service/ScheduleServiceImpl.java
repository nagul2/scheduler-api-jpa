package spring.advanced.schedulerjpa.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.advanced.schedulerjpa.schedule.domain.dto.ScheduleCommonResponseDto;
import spring.advanced.schedulerjpa.schedule.domain.dto.ScheduleFindResponseDto;
import spring.advanced.schedulerjpa.schedule.domain.dto.ScheduleUpdateRequestDto;
import spring.advanced.schedulerjpa.schedule.domain.entity.Schedule;
import spring.advanced.schedulerjpa.schedule.repository.ScheduleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Override
    @Transactional
    public ScheduleCommonResponseDto saveSchedule(String writer, String title, String content) {
        Schedule schedule = Schedule.builder()
                .writer(writer)
                .title(title)
                .content(content)
                .build();

        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleCommonResponseDto(savedSchedule.getId());
    }

    @Override
    public List<ScheduleFindResponseDto> findAllSchedules() {

        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules.stream().map(ScheduleFindResponseDto::mapToDto).toList();
    }

    @Override
    public ScheduleFindResponseDto findScheduleById(Long id) {
        // 필수 구현 단계에서는 예외 처리 X, 도전 때 구현
        Schedule findSchedule = scheduleRepository.findById(id).orElse(null);
        if (findSchedule == null) {
            return null;
        }

        return ScheduleFindResponseDto.mapToDto(findSchedule);
    }

    @Override
    @Transactional
    public ScheduleCommonResponseDto updateSchedule(Long id, ScheduleUpdateRequestDto requestDto) {
        // 필수 구현 단계에서는 예외 처리 X, 도전 때 구현
        Schedule findSchedule = scheduleRepository.findById(id).orElse(null);

        if (findSchedule == null) {
            return null;
        }

        findSchedule.updateSchedule(requestDto);

        return new ScheduleCommonResponseDto(id);
    }
}
