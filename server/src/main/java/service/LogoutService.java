package service;

import dataaccess.AuthDatabaseDAO;
import exceptions.UnauthorizedException;
import request.LogoutRequest;
import response.EmptyResponse;

public class LogoutService {

    public EmptyResponse logout(LogoutRequest logoutRequest) throws Exception {
        AuthDatabaseDAO aDAO = new AuthDatabaseDAO();
        if (ServiceUtils.isABadToken(aDAO, logoutRequest)) { throw new UnauthorizedException("Error: unauthorized"); }
        try {
            aDAO.deleteAuth(logoutRequest.authToken());
            return new EmptyResponse();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
