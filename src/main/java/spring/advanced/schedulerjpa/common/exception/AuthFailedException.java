package spring.advanced.schedulerjpa.common.exception;

public class AuthFailedException extends RuntimeException {
    public AuthFailedException(String message) {
        super(message);
    }
}
