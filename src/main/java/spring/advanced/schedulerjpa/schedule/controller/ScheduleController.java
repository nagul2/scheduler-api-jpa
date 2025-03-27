package spring.advanced.schedulerjpa.schedule.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.advanced.schedulerjpa.schedule.domain.dto.ScheduleCommonResponseDto;
import spring.advanced.schedulerjpa.schedule.domain.dto.ScheduleCreateRequestDto;
import spring.advanced.schedulerjpa.schedule.domain.dto.ScheduleFindResponseDto;
import spring.advanced.schedulerjpa.schedule.service.ScheduleService;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleCommonResponseDto> addSchedule(@RequestBody ScheduleCreateRequestDto requestDto) {

        String writer = requestDto.writer();
        String title = requestDto.title();
        String content = requestDto.content();

        return new ResponseEntity<>(scheduleService.saveSchedule(writer, title, content), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleFindResponseDto>> findAllSchedules() {
        return new ResponseEntity<>(scheduleService.findAllSchedules(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleFindResponseDto> findScheduleById(@PathVariable Long id) {

        return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK);
    }
}
