package spring.advanced.schedulerjpa.schedule.domain.dto;

import jakarta.validation.constraints.NotNull;

public record ScheduleCreateRequestDto(
        @NotNull
        String title,
        String content) {

}
