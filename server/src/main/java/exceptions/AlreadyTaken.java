package exceptions;

// [403] Error Exception

public class AlreadyTaken extends RuntimeException {
    public AlreadyTaken(String message) {
        super(message);
    }
}
