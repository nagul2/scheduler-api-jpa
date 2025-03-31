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
     * 중복된 Username 으로 로그인을 시도하거나 강제로 Username 검증을 건너뛰고 유저를 저장 시도할 때 예외 응답
     *
     * @param e 중복된 유저 예외(조상이 RuntimeException)
     * @return ErrorDto, 400 상태코드
     */
    @ExceptionHandler({DuplicatedUserException.class, DuplicatedUsernameException.class})
    public ResponseEntity<ErrorDto> duplicatedUserException(RuntimeException e) {
        log.error("[duplicatedUserException] ex: ", e);
        ErrorDto errorDto = new ErrorDto(ErrorCode.LOGIN_FAILED.getCode(), ErrorCode.LOGIN_FAILED, e.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(errorDto, ErrorCode.LOGIN_FAILED.getHttpStatus());
    }

    /**
     * 유저나 일정을 찾지 못할 경우 발생되는 예외 응답
     *
     * @param e 유저, 일정 못찾는 예외(조상이 RuntimeException)
     * @return ErrorDto, 404 상태코드
     */
    @ExceptionHandler({NotFoundScheduleException.class, NotFoundUserException.class, NotFoundCommentException.class})
    public ResponseEntity<ErrorDto> notFoundException(RuntimeException e) {
        log.error("[notFoundException] ex: ", e);
        ErrorDto errorDto = new ErrorDto(ErrorCode.LOGIN_FAILED.getCode(), ErrorCode.LOGIN_FAILED, e.getMessage(), LocalDateTime.now());

        return new ResponseEntity<>(errorDto, ErrorCode.LOGIN_FAILED.getHttpStatus());
    }

    /**
     * 입력값 검증 예외 응답
     *
     * @param e Bean Validation 예외
     * @return ErrorDto, 400 상태코드
     */
    @ExceptionHandler({})
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
