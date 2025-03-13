package service;

import chess.ChessGame;
import dataaccess.*;
import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
import model.GameData;
import request.CreateGameRequest;
import response.CreateGameResponse;

public class CreateGameService {
    public CreateGameResponse createGame(CreateGameRequest createGameRequest) throws Exception {
        AuthDAO aDAO = new AuthSQLDAO();
        GameDAO gDAO = new GameSQLDAO();
        if (ServiceUtils.isABadRequest(createGameRequest)) { throw new BadRequestException("Error: bad request"); }
        if (ServiceUtils.isABadToken(aDAO, createGameRequest)) { throw new UnauthorizedException("Error: unauthorized"); }
        try {
            int id = ServiceUtils.generateGameID();
            ChessGame chessGame = new ChessGame();
            gDAO.storeGame(new GameData(id, null, null, createGameRequest.gameName(), chessGame));
            return new CreateGameResponse(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
