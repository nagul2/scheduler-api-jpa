package spring.advanced.schedulerjpa.schedule.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.advanced.schedulerjpa.auth.dto.AuthLoginResponseDto;
import spring.advanced.schedulerjpa.common.constant.AuthConst;
import spring.advanced.schedulerjpa.schedule.domain.dto.*;
import spring.advanced.schedulerjpa.schedule.service.ScheduleService;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    /**
     * 일정 생성 API
     *
     * @param requestDto 일정 생성 요청 정보
     * @param servletRequest 서블릿 요청 (세션정보 꺼내기 위함)
     * @return 생성된 일정의 정보를 담은 DTO, 성공시 201 응답
     */
    @PostMapping
    public ResponseEntity<ScheduleCommonResponseDto> addSchedule(
            @Valid @RequestBody ScheduleCreateRequestDto requestDto,
            HttpServletRequest servletRequest) {

        AuthLoginResponseDto loginDto = (AuthLoginResponseDto) servletRequest.getSession().getAttribute(AuthConst.LOGIN_MEMBER);

        Long userId = loginDto.id();
        String title = requestDto.title();
        String content = requestDto.content();

        return new ResponseEntity<>(scheduleService.saveSchedule(userId, title, content), HttpStatus.CREATED);
    }

    /**
     * 일정 전체 조회 API
     * 페이징 적용(기본값 = 페이지당 size 10, 수정일 기준 내림차순 적용)
     * 각 일정의 댓글 정보 포함하여 반환함
     *
     * @param pageable 페이징 정보
     * @return 조회된 일정 정보를 Page로 반환, 성공시 200 응답
     */
    @GetMapping
    public ResponseEntity<Page<ScheduleFindAllPagingResponseDto>> findAllSchedules(
            @PageableDefault(size = 10, sort = "modifyAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return new ResponseEntity<>(scheduleService.findAllSchedules(pageable), HttpStatus.OK);
    }

    /**
     * 일정 단건 조회 API
     *
     * @param id 조회할 일정 id
     * @return 조회된 일정, 성공시 200 응답
     */
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleFindResponseDto> findScheduleById(@PathVariable Long id) {

        return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK);
    }

    /**
     * 일정 수정 API
     *
     * @param id 수정할 일정의 id
     * @param requestDto 수정할 일정 정보
     * @return 수정된 일정정보를 담은 DTO, 성공시 200 응답
     */
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleCommonResponseDto> updateSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleUpdateRequestDto requestDto) {

        String content = requestDto.content();
        String title = requestDto.title();

        return new ResponseEntity<>(scheduleService.updateSchedule(id, title, content), HttpStatus.OK);
    }

    /**
     * 일정 삭제 API
     *
     * @param id 삭제할 일정 id
     * @return 성공시 200 응답
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
