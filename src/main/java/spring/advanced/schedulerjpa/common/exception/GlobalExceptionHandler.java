package spring.advanced.schedulerjpa.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorDto> authFailedException(AuthFailedException e) {
        log.error("[authFiledException] ex: ", e);
        ErrorDto errorDto = new ErrorDto(ErrorCode.LOGIN_FAILED.getCode(), ErrorCode.LOGIN_FAILED, e.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(errorDto, ErrorCode.LOGIN_FAILED.getHttpStatus());
    }
}
