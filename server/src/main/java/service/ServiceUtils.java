package service;

import dataaccess.interfaces.AuthDAO;
import dataaccess.interfaces.GameDAO;
import dataaccess.interfaces.UserDAO;
import exceptions.DataAccessException;
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

    public static boolean isABadRequest(GameDAO gDAO, JoinGameRequest joinGameRequest) throws DataAccessException {
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

    private static boolean invalidID(GameDAO gDAO, int id) throws DataAccessException {
        boolean gameExists = gDAO.gameExists(id);
        return !gameExists;
    }

    public static boolean gameColorTaken(GameDAO gDAO, JoinGameRequest joinGameRequest) throws DataAccessException {
        GameData game = gDAO.retrieveGame(joinGameRequest.gameID());
        String playerColor = joinGameRequest.playerColor();
         return
                playerColor.equals("WHITE") && game.whiteUsername() != null ||
                playerColor.equals("BLACK") && game.blackUsername() != null;
    }

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public static boolean isABadToken(AuthDAO aDAO, AuthRequest request) throws DataAccessException {
        return !aDAO.authenticateAuth(request.authToken());
    }

    public static boolean isABadUser(UserDAO uDAO, LoginRequest loginRequest) throws DataAccessException {
        return !uDAO.authenticateUser(loginRequest.username(), loginRequest.password());
    }

    public static Boolean isABadRequest(RegisterRequest registerRequest) {
        String username = registerRequest.username();
        String password = registerRequest.password();
        String email = registerRequest.email();
        return username == null || password == null || email == null || username.isEmpty() || password.isEmpty() || email.isEmpty();
    }

    public static Boolean usernameTaken(UserDAO uDAO, RegisterRequest registerRequest) throws DataAccessException {
        return uDAO.userExists(registerRequest.username());
    }
}
