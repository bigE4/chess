package service;

import dataaccess.AuthDatabaseDAO;
import exceptions.UnauthorizedException;
import request.LogoutRequest;
import response.EmptyResponse;

public class LogoutService {

    public EmptyResponse logout(LogoutRequest logoutRequest) throws Exception {
        AuthDatabaseDAO aDAO = new AuthDatabaseDAO();
        String authToken = logoutRequest.authToken();
        if (!aDAO.AuthenticateAuth(authToken)) { throw new UnauthorizedException("Error: unauthorized"); }
        aDAO.DeleteAuth(authToken);
        return new EmptyResponse();
    }
}
