package service;

import dataaccess.*;
import dataaccess.interfaces.AuthDAO;
import dataaccess.interfaces.GameDAO;
import exceptions.AlreadyTakenException;
import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
import model.GameData;
import request.JoinGameRequest;
import response.EmptyResponse;

public class JoinGameService {
    public EmptyResponse joinGame(JoinGameRequest joinGameRequest) throws Exception {
        AuthDAO aDAO = new AuthSQLDAO();
        GameDAO gDAO = new GameSQLDAO();
        if (ServiceUtils.isABadRequest(gDAO, joinGameRequest)) { throw new BadRequestException("Error: bad request"); }
        if (ServiceUtils.isABadToken(aDAO, joinGameRequest)) { throw new UnauthorizedException("Error: unauthorized"); }
        if (ServiceUtils.gameColorTaken(gDAO, joinGameRequest)) { throw new AlreadyTakenException("Error: already taken"); }
        try {
            String authToken = joinGameRequest.authToken();
            String playerColor = joinGameRequest.playerColor();
            int id = joinGameRequest.gameID();
            GameData oldGameData = gDAO.retrieveGame(id);
            String username = aDAO.retrieveAuth(authToken).username();
            GameData newGameData = getGameData(playerColor, oldGameData, username);
            gDAO.updatePlayerColors(newGameData);
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
