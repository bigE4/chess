package service;

import chess.ChessGame;
import chess.ChessMove;
import dataaccess.*;
import dataaccess.interfaces.AuthDAO;
import dataaccess.interfaces.GameDAO;
import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
import model.GameData;
import request.CreateGameRequest;
import response.CreateGameResponse;

import java.util.ArrayList;
import java.util.List;

public class CreateGameService {
    public CreateGameResponse createGame(CreateGameRequest createGameRequest) throws Exception {
        AuthDAO aDAO = new AuthSQLDAO();
        GameDAO gDAO = new GameSQLDAO();
        if (ServiceUtils.isABadRequest(createGameRequest)) { throw new BadRequestException("Error: bad request"); }
        if (ServiceUtils.isABadToken(aDAO, createGameRequest)) { throw new UnauthorizedException("Error: unauthorized"); }
        try {
            int id = ServiceUtils.generateGameID();
            ChessGame chessGame = new ChessGame();
            List<ChessMove> moves = new ArrayList<>();
            gDAO.storeGame(new GameData(id, null, null, createGameRequest.gameName(), chessGame, moves));
            return new CreateGameResponse(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
