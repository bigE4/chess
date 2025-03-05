package service;

import dataaccess.AuthDatabaseDAO;
import dataaccess.GameDatabaseDAO;
import exceptions.AlreadyTakenException;
import exceptions.BadRequestException;
import exceptions.UnauthorizedException;
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
            // Implement JoinGame Business Logic Here
            return new EmptyResponse();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
