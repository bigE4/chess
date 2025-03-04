package exceptions;

// [403] Error Exception

public class AlreadyTakenException extends RuntimeException {
    public AlreadyTakenException(String message) {
        super(message);
    }
}
