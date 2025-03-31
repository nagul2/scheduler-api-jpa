package spring.advanced.schedulerjpa.common.exception;

public class NotFoundScheduleException extends RuntimeException {
    public NotFoundScheduleException(String message) {
        super(message);
    }
}
