package exceptions;

// [403] Error Exception

public class UsernameUnavailableException extends RuntimeException {
    public UsernameUnavailableException(String message) {
        super(message);
    }
}
