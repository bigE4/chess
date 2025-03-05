package service;

import dataaccess.AuthDatabaseDAO;
import dataaccess.GameDatabaseDAO;
import dataaccess.UserDatabaseDAO;
import request.AuthRequest;
import request.CreateGameRequest;
import request.LoginRequest;
import request.RegisterRequest;
import java.util.Random;
import java.util.UUID;

public class ServiceUtils {

    public static String GenerateToken() {
        return UUID.randomUUID().toString();
    }

    public static int GenerateGameID() {
        Random random = new Random();
        return random.nextInt(1000, 9999);
    }

    public static boolean AuthenticateToken(AuthDatabaseDAO aDAO, AuthRequest request) {
        return aDAO.AuthenticateAuth(request.authToken());
    }

    public static boolean AuthenticateUser(UserDatabaseDAO uDAO, LoginRequest loginRequest) {
        return uDAO.AuthenticateUser(loginRequest.username(), loginRequest.password());
    }

    public static Boolean BadRequest(CreateGameRequest createGameRequest) {
        String authToken = createGameRequest.authToken();
        String gameName = createGameRequest.gameName();
        return authToken == null || gameName == null || authToken.isEmpty() || gameName.isEmpty();
    }

    public static Boolean GameNameUnavailable(GameDatabaseDAO gDAO, CreateGameRequest createGameRequest) {
        return gDAO.GameExists(createGameRequest.gameName());
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
