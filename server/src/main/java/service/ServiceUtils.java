package service;

import dataaccess.AuthDatabaseDAO;
import dataaccess.GameDatabaseDAO;
import dataaccess.UserDatabaseDAO;
import model.GameData;
import request.*;

import java.util.Objects;
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

    public static Boolean BadRequest(JoinGameRequest joinGameRequest) {
        String authToken = joinGameRequest.authToken();
        String playerColor = joinGameRequest.playerColor();
        int ID = joinGameRequest.gameID();
        return authToken == null || playerColor == null || authToken.isEmpty() || playerColor.isEmpty() || InvalidColor(playerColor) || InvalidID(ID);
    }

    private static Boolean InvalidColor(String playerColor) {
        String white = "WHITE";
        String black = "BLACK";
        return !(playerColor.equals(white) || playerColor.equals(black));
    }

    private static Boolean InvalidID(int ID) {
        return !(1000 <= ID && ID <= 9999);
    }

    public static boolean GameColorUnavailable(GameDatabaseDAO gDAO, JoinGameRequest joinGameRequest) {
        GameData game = gDAO.RetrieveGame(joinGameRequest.gameID());
        String playerColor = joinGameRequest.playerColor();
        return playerColor.equals("WHITE") && game.whiteUsername() != null || playerColor.equals("BLACK") && game.blackUsername() != null;
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
