package spring.advanced.schedulerjpa.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // Auth
    LOGIN_FAILED("401", HttpStatus.UNAUTHORIZED, "로그인 실패, 아이디나 비밀번호를 확인해 주세요"),
    UNAUTHORIZED_ACCESS("401", HttpStatus.UNAUTHORIZED, "인증되지 않은 접근입니다. 로그인 후 시도해 주세요"),

    // Valid
    VALID_BAD_REQUEST("400",HttpStatus.BAD_REQUEST, "잘못된 입력값 입니다."),

    // Schedule
    SCHEDULE_NOT_FOUND("404", HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다"),

    // User
    USER_NOT_FOUND("404", HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다"),
    ;


    private final String code;
    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(String code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
