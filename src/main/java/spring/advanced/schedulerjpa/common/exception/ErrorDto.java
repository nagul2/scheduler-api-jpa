package spring.advanced.schedulerjpa.common.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record ErrorDto(
        String code,
        ErrorCode error,
        String message,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime errorTime
) {
}
