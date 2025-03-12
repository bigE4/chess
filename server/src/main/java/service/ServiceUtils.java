package service;

import dataaccess.*;
import model.GameData;
import request.*;

import java.util.Random;
import java.util.UUID;

public class ServiceUtils {

    public static int generateGameID() {
        Random random = new Random();
        return random.nextInt(1000, 9999);
    }

    public static boolean isABadRequest(CreateGameRequest createGameRequest) {
        String authToken = createGameRequest.authToken();
        String gameName = createGameRequest.gameName();
        return authToken == null || gameName == null || authToken.isEmpty() || gameName.isEmpty();
    }

    public static boolean isABadRequest(GameDAO gDAO, JoinGameRequest joinGameRequest) {
        String authToken = joinGameRequest.authToken();
        String playerColor = joinGameRequest.playerColor();
        int id = joinGameRequest.gameID();
        return
                authToken == null || playerColor == null ||
                authToken.isEmpty() || playerColor.isEmpty() ||
                invalidColor(playerColor) || invalidID(gDAO, id);
    }

    private static boolean invalidColor(String playerColor) {
        String white = "WHITE";
        String black = "BLACK";
        return !(playerColor.equals(white) || playerColor.equals(black));
    }

    private static boolean invalidID(GameDAO gDAO, int id) {
        boolean gameExists = gDAO.gameExists(id);
        return !gameExists;
    }

    public static boolean gameColorTaken(GameDAO gDAO, JoinGameRequest joinGameRequest) {
        GameData game = gDAO.retrieveGame(joinGameRequest.gameID());
        String playerColor = joinGameRequest.playerColor();
        return
                playerColor.equals("WHITE") && game.whiteUsername() != null ||
                playerColor.equals("BLACK") && game.blackUsername() != null;
    }

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public static boolean isABadToken(AuthDAO aDAO, AuthRequest request) {
        return !aDAO.authenticateAuth(request.authToken());
    }

    public static boolean isABadUser(UserDAO uDAO, LoginRequest loginRequest) {
        return !uDAO.authenticateUser(loginRequest.username(), loginRequest.password());
    }

    public static Boolean isABadRequest(RegisterRequest registerRequest) {
        String username = registerRequest.username();
        String password = registerRequest.password();
        String email = registerRequest.email();
        return username == null || password == null || email == null || username.isEmpty() || password.isEmpty() || email.isEmpty();
    }

    public static Boolean usernameTaken(UserDAO uDAO, RegisterRequest registerRequest) {
        return uDAO.userExists(registerRequest.username());
    }
}
