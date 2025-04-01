package spring.advanced.schedulerjpa.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.advanced.schedulerjpa.comment.repository.CommentRepository;
import spring.advanced.schedulerjpa.common.exception.ErrorCode;
import spring.advanced.schedulerjpa.common.exception.NotFoundCommentException;
import spring.advanced.schedulerjpa.common.exception.NotFoundScheduleException;
import spring.advanced.schedulerjpa.common.exception.NotFoundUserException;
import spring.advanced.schedulerjpa.schedule.domain.dto.ScheduleCommonResponseDto;
import spring.advanced.schedulerjpa.schedule.domain.dto.ScheduleFindAllPagingResponseDto;
import spring.advanced.schedulerjpa.schedule.domain.dto.ScheduleFindResponseDto;
import spring.advanced.schedulerjpa.schedule.domain.entity.Schedule;
import spring.advanced.schedulerjpa.schedule.repository.ScheduleRepository;
import spring.advanced.schedulerjpa.user.domain.entity.User;
import spring.advanced.schedulerjpa.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    /**
     * 일정 생성 로직
     * 유저가 없다면 USER_NOT_FOUND 예외 적용
     *
     * @param userId 유저 id
     * @param title 생성할 일정 제목
     * @param content 생성할 일정 내용
     * @return 생성된 일정 id를 DTO에 담아서 반환
     */
    @Override
    @Transactional
    public ScheduleCommonResponseDto saveSchedule(Long userId, String title, String content) {

        User findUser = userRepository.findById(userId).orElseThrow(
                () ->new NotFoundUserException(ErrorCode.USER_NOT_FOUND.getMessage())
        );

        Schedule schedule = Schedule.builder()
                .user(findUser)
                .title(title)
                .content(content)
                .build();

        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleCommonResponseDto(savedSchedule.getId());
    }

    /**
     * 일정 전체 조회 로직
     * 조회된 일정이 없으면 SCHEDULE_NOT_FOUND 예외 적용
     *
     * @param pageable 페이징 정보
     * @return 조회된 일정 정보를 Page객체로 반환
     */
    @Override
    public Page<ScheduleFindAllPagingResponseDto> findAllSchedules(Pageable pageable) {

        Page<ScheduleFindAllPagingResponseDto> findAllWithCommentCount = scheduleRepository.findAllWithCommentCount(pageable);

        if (findAllWithCommentCount.isEmpty()) {
            throw new NotFoundScheduleException(ErrorCode.SCHEDULE_NOT_FOUND.getMessage());
        }
        return findAllWithCommentCount;
    }

    /**
     * 일정 단건 조회 로직
     * 조회된 일정이 없으면 SCHEDULE_NOT_FOUND 예외 적용
     *
     * @param id 조회할 일정 id
     * @return 조회된 일정을 DTO로 변환하여 반환
     */
    @Override
    public ScheduleFindResponseDto findScheduleById(Long id) {
        Schedule findSchedule = findScheduleOrElseThrow(id);
        return ScheduleFindResponseDto.mapToDto(findSchedule);
    }

    /**
     * 일정 수정 로직
     * 조회된 일정이 없으면 SCHEDULE_NOT_FOUND 예외 적용
     *
     * @param id 수정할 일정 id
     * @param title 수정할 일정 제목
     * @param content 수정할 일정 내용
     * @return 수정된 일정의 id를 DTO에 담아서 반환
     */
    @Override
    @Transactional
    public ScheduleCommonResponseDto updateSchedule(Long id, String title, String content) {
        Schedule findSchedule = findScheduleOrElseThrow(id);
        findSchedule.updateSchedule(title, content);

        return new ScheduleCommonResponseDto(id);
    }

    /**
     * 조회된 일정이 없으면 SCHEDULE_NOT_FOUND 예외 적용
     *
     * @param id 수정할 일정 id
     */
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
