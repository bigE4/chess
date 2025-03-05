package service;

import chess.ChessGame;
import dataaccess.AuthDatabaseDAO;
import dataaccess.GameDatabaseDAO;
import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
import model.GameData;
import request.CreateGameRequest;
import response.CreateGameResponse;

public class CreateGameService {
    public CreateGameResponse CreateGame(CreateGameRequest createGameRequest) throws Exception {
        AuthDatabaseDAO aDAO = new AuthDatabaseDAO();
        GameDatabaseDAO gDAO = new GameDatabaseDAO();
        if (ServiceUtils.IsABadRequest(createGameRequest)) { throw new BadRequestException("Error: bad request"); }
        if (ServiceUtils.IsABadToken(aDAO, createGameRequest)) { throw new UnauthorizedException("Error: unauthorized"); }
        try {
            int ID = ServiceUtils.GenerateGameID();
            ChessGame chessGame = new ChessGame();
            gDAO.StoreGame(new GameData(ID, null, null, createGameRequest.gameName(), chessGame));
            return new CreateGameResponse(ID);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
