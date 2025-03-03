package exceptions;

// [401] Error Exception

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
