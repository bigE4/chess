package service;
import dataaccess.UserDatabaseDAO;
import request.RegisterRequest;

import java.util.UUID;

public class ServiceUtils {

    public static String GenerateToken() {
        return UUID.randomUUID().toString();
    }

    public static Boolean BadRequest(RegisterRequest registerRequest) {
        String username = registerRequest.username();
        String password = registerRequest.password();
        String email = registerRequest.email();
        return username == null || password == null || email == null || username.isEmpty() || password.isEmpty() || email.isEmpty();
    }

    public static Boolean UsernameUnavailable(UserDatabaseDAO uDAO, RegisterRequest registerRequest) {
        return uDAO.UserExists(registerRequest.username());
    }
}
