package service;

import dataaccess.AuthDatabaseDAO;
import dataaccess.GameDatabaseDAO;
import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
import request.CreateGameRequest;
import response.CreateGameResponse;

public class CreateGameService {
    public CreateGameResponse CreateGame(CreateGameRequest createGameRequest) throws Exception {
        AuthDatabaseDAO aDAO = new AuthDatabaseDAO();
        GameDatabaseDAO gDAO = new GameDatabaseDAO();
        if (ServiceUtils.BadRequest(createGameRequest)) { throw new BadRequestException("Error: bad request"); }
        if (ServiceUtils.AuthenticateToken(aDAO, createGameRequest)) { throw new UnauthorizedException("Error: unauthorized"); }
        try {
            int ID = ServiceUtils.GenerateGameID();
            // Implement CreateGame Business Logic Here
            return new CreateGameResponse(ID);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
