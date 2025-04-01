package spring.advanced.schedulerjpa.common.exception;

public class AuthFailedException extends RuntimeException {

    private final ErrorCode errorCode;

    public AuthFailedException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
