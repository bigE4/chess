package service;

import dataaccess.AuthDatabaseDAO;
import exceptions.UnauthorizedException;
import request.LogoutRequest;
import response.EmptyResponse;

public class LogoutService {
    private final AuthDatabaseDAO aDAO = new AuthDatabaseDAO();

    public EmptyResponse logout(LogoutRequest logoutRequest) throws Exception {
        String authToken = logoutRequest.authToken();

        if (!aDAO.AuthenticateAuth(authToken)) {
            throw new UnauthorizedException("Error: unauthorized");
        }

        aDAO.DeleteAuth(authToken);
        return new EmptyResponse(); // Successfully logged out
    }
}
