package spring.advanced.schedulerjpa.schedule.domain.dto;

public record ScheduleCreateRequestDto(
        Long userId,
        String title,
        String content) {

}
