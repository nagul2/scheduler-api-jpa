package spring.advanced.schedulerjpa.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 인증 실패 예외 응답
     * 상태코드, 상태 메시지, 예외 메시지, 일자를 담은 DTO객체와 상태코드를 ResponseEntity로 반환
     *
     * @param e 인증 실패 예외
     * @return ErrorDto, 401 상태코드
     */
    @ExceptionHandler
    public ResponseEntity<ErrorDto> authFailedException(AuthFailedException e) {
        log.error("[authFiledException] ex: ", e);
        ErrorDto errorDto = new ErrorDto(ErrorCode.LOGIN_FAILED.getCode(), ErrorCode.LOGIN_FAILED, e.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(errorDto, ErrorCode.LOGIN_FAILED.getHttpStatus());
    }

    /**
     * 입력값 검증 예외 응답
     * 발생된 검증 예외들의 중복에러를 제외한 필드와, 메시지를 Map으로 변환하여 상태코드와 함께 ErrorDto로 변환 후 ResponseEntity로 반환
     *
     * @param e Bean Validation 예외
     * @return ErrorDto, 400 상태코드
     */
    @ExceptionHandler
    public ResponseEntity<ErrorDto> inputValidException(MethodArgumentNotValidException e) {
        log.error("[inputValidException] ex: ", e);

        Map<String, String> errors = e.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (msg1, msg2) -> msg1
                ));

        ErrorDto errorDto = new ErrorDto(ErrorCode.VALID_BAD_REQUEST.getCode(), ErrorCode.VALID_BAD_REQUEST, errors.toString(), LocalDateTime.now());
        return new ResponseEntity<>(errorDto, ErrorCode.VALID_BAD_REQUEST.getHttpStatus());

    }
}
