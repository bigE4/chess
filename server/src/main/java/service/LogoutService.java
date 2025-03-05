package service;

import dataaccess.AuthDatabaseDAO;
import exceptions.UnauthorizedException;
import request.LogoutRequest;
import response.EmptyResponse;

public class LogoutService {

    public EmptyResponse Logout(LogoutRequest logoutRequest) throws Exception {
        AuthDatabaseDAO aDAO = new AuthDatabaseDAO();
        if (ServiceUtils.IsABadToken(aDAO, logoutRequest)) { throw new UnauthorizedException("Error: unauthorized"); }
        try {
            aDAO.DeleteAuth(logoutRequest.authToken());
            return new EmptyResponse();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
