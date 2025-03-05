package service;

import dataaccess.AuthDatabaseDAO;
import dataaccess.GameDatabaseDAO;
import exceptions.AlreadyTakenException;
import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
import request.CreateGameRequest;
import response.CreateGameResponse;

public class CreateGameService {
    public CreateGameResponse CreateGame(CreateGameRequest createGameRequest) throws Exception {
        AuthDatabaseDAO aDAO = new AuthDatabaseDAO();
        GameDatabaseDAO gDAO = new GameDatabaseDAO();
        String authToken = createGameRequest.authToken();
        if (ServiceUtils.BadRequest(createGameRequest)) { throw new BadRequestException("Error: bad request"); }
        if (ServiceUtils.AuthenticateToken(aDAO, createGameRequest)) { throw new UnauthorizedException("Error: unauthorized"); }
        if (ServiceUtils.GameNameUnavailable(gDAO, createGameRequest)) { throw new AlreadyTakenException("Error: already taken"); }
        try {
            return null;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
