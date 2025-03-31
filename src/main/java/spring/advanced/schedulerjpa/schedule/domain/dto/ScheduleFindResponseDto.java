package spring.advanced.schedulerjpa.schedule.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import spring.advanced.schedulerjpa.schedule.domain.entity.Schedule;

import java.time.LocalDateTime;

public record ScheduleFindResponseDto(
        Long id,
        String username,
        String title,
        String content,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime modifyAt
        ) {

    static public ScheduleFindResponseDto mapToDto(Schedule schedule) {
        return new ScheduleFindResponseDto(
                schedule.getId(),
                schedule.getUser().getUsername(),
                schedule.getTitle(),
                schedule.getContent(),
                schedule.getModifyAt()
        );
    }

}
