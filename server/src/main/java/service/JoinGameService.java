package service;

import dataaccess.AuthDatabaseDAO;
import dataaccess.GameDatabaseDAO;
import exceptions.AlreadyTakenException;
import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
import model.GameData;
import request.JoinGameRequest;
import response.EmptyResponse;

public class JoinGameService {
    public EmptyResponse JoinGame(JoinGameRequest joinGameRequest) throws Exception {
        AuthDatabaseDAO aDAO = new AuthDatabaseDAO();
        GameDatabaseDAO gDAO = new GameDatabaseDAO();
        if (ServiceUtils.BadRequest(joinGameRequest)) { throw new BadRequestException("Error: bad request"); }
        if (!ServiceUtils.AuthenticateToken(aDAO, joinGameRequest)) { throw new UnauthorizedException("Error: unauthorized"); }
        if (ServiceUtils.GameColorUnavailable(gDAO, joinGameRequest)) { throw new AlreadyTakenException("Error: already taken"); }
        try {
            String authToken = joinGameRequest.authToken();
            String playerColor = joinGameRequest.playerColor();
            int ID = joinGameRequest.gameID();
            GameData oldGameData = gDAO.RetrieveGame(ID);
            String username = aDAO.RetrieveAuth(authToken).username();
            GameData newGameData = getGameData(playerColor, oldGameData, username);
            gDAO.UpdateGame(newGameData);
            return new EmptyResponse();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private static GameData getGameData(String playerColor, GameData oldGameData, String username) {
        GameData newGameData;
        if (playerColor.equals("WHITE")) {
            newGameData = new GameData(oldGameData.gameID(), username, oldGameData.blackUsername(), oldGameData.gameName(), oldGameData.game());
        } else {
            newGameData = new GameData(oldGameData.gameID(), oldGameData.whiteUsername(), username, oldGameData.gameName(), oldGameData.game());
        }
        return newGameData;
    }
}
