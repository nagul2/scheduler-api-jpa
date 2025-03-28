package spring.advanced.schedulerjpa.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.advanced.schedulerjpa.schedule.domain.dto.ScheduleCommonResponseDto;
import spring.advanced.schedulerjpa.schedule.domain.dto.ScheduleFindResponseDto;
import spring.advanced.schedulerjpa.schedule.domain.entity.Schedule;
import spring.advanced.schedulerjpa.schedule.repository.ScheduleRepository;
import spring.advanced.schedulerjpa.user.domain.entity.User;
import spring.advanced.schedulerjpa.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ScheduleCommonResponseDto saveSchedule(Long userId, String title, String content) {

        User findUser = userRepository.findById(userId).orElse(null);

        Schedule schedule = Schedule.builder()
                .user(findUser)
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
    public ScheduleCommonResponseDto updateSchedule(Long id, String title, String content) {
        // 필수 구현 단계에서는 예외 처리 X, 도전 때 구현
        Schedule findSchedule = scheduleRepository.findById(id).orElse(null);

        if (findSchedule == null) {
            return null;
        }

        findSchedule.updateSchedule(title, content);

        return new ScheduleCommonResponseDto(id);
    }

    @Override
    @Transactional
    public void deleteSchedule(Long id) {
        // 필수 구현 단계에서는 예외 처리 X, 도전 때 구현
        Schedule findSchedule = scheduleRepository.findById(id).orElse(null);

        if (findSchedule == null) {
            return;
        }

        scheduleRepository.delete(findSchedule);
    }
}
