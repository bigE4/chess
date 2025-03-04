package service;
import java.util.UUID;

public class AuthTokenGenerator {
    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
}
