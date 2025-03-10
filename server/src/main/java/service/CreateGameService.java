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
    public CreateGameResponse createGame(CreateGameRequest createGameRequest) throws Exception {
        AuthDatabaseDAO aDAO = new AuthDatabaseDAO();
        GameDatabaseDAO gDAO = new GameDatabaseDAO();
        if (ServiceUtils.isABadRequest(createGameRequest)) { throw new BadRequestException("Error: bad request"); }
        if (ServiceUtils.isABadToken(aDAO, createGameRequest)) { throw new UnauthorizedException("Error: unauthorized"); }
        try {
            int ID = ServiceUtils.generateGameID();
            ChessGame chessGame = new ChessGame();
            gDAO.storeGame(new GameData(ID, null, null, createGameRequest.gameName(), chessGame));
            return new CreateGameResponse(ID);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
