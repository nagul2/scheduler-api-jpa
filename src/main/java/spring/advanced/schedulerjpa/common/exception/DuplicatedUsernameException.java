package spring.advanced.schedulerjpa.common.exception;

public class DuplicatedUsernameException extends RuntimeException {
    public DuplicatedUsernameException(String message) {
        super(message);
    }
}
