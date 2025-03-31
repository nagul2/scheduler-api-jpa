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
import spring.advanced.schedulerjpa.common.consant.AuthConst;
import spring.advanced.schedulerjpa.schedule.domain.dto.*;
import spring.advanced.schedulerjpa.schedule.service.ScheduleService;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

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

    @GetMapping
    public ResponseEntity<Page<ScheduleFindAllPagingResponseDto>> findAllSchedules(
            @PageableDefault(size = 10, sort = "modifyAt", direction = Sort.Direction.DESC) Pageable pageable) {

        return new ResponseEntity<>(scheduleService.findAllSchedules(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleFindResponseDto> findScheduleById(@PathVariable Long id) {

        return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleCommonResponseDto> updateSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleUpdateRequestDto requestDto) {

        String content = requestDto.content();
        String title = requestDto.title();

        return new ResponseEntity<>(scheduleService.updateSchedule(id, title, content), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
