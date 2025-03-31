package spring.advanced.schedulerjpa.schedule.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import spring.advanced.schedulerjpa.schedule.domain.entity.Schedule;

import java.time.LocalDateTime;

public record ScheduleFindAllPagingResponseDto(
        Long id,
        String username,
        String title,
        String content,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createAt,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime modifyAt,
        Long commentCount
        ) {
}
