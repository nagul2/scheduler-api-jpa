package spring.advanced.schedulerjpa.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.advanced.schedulerjpa.common.exception.ErrorCode;
import spring.advanced.schedulerjpa.common.exception.NotFoundScheduleException;
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
        Schedule findSchedule = findScheduleOrElseThrow(id);
        return ScheduleFindResponseDto.mapToDto(findSchedule);
    }

    @Override
    @Transactional
    public ScheduleCommonResponseDto updateSchedule(Long id, String title, String content) {
        Schedule findSchedule = findScheduleOrElseThrow(id);
        findSchedule.updateSchedule(title, content);

        return new ScheduleCommonResponseDto(id);
    }

    @Override
    @Transactional
    public void deleteSchedule(Long id) {
        Schedule findSchedule = findScheduleOrElseThrow(id);
        scheduleRepository.delete(findSchedule);
    }

    /**
     * scheduleRepository.findById(id).orElseThrow()로 못찾으면 에러를 던지고,
     * 찾으면 일정을 반환하는 코드를 메서드화하여 코드 중복을 해결하고 가독성있게 해결
     *
     * @param id 찾고자 할 일정의 아이디값
     * @return 찾으면 Schedule, 못 찾으면 NotFoundScheduleException
     */
    private Schedule findScheduleOrElseThrow(Long id) {
        return scheduleRepository.findById(id).orElseThrow(
                () -> new NotFoundScheduleException(ErrorCode.SCHEDULE_NOT_FOUND.getMessage())
        );
    }
}
