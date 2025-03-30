package spring.advanced.schedulerjpa.schedule.domain.dto;

import jakarta.validation.constraints.NotNull;

public record ScheduleCreateRequestDto(
        Long userId,

        @NotNull
        String title,
        String content) {

}
