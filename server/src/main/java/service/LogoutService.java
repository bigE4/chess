package service;

import dataaccess.dao.AuthSQLDAO;
import exceptions.UnauthorizedException;
import records.LogoutRequest;
import records.EmptyResponse;

public class LogoutService {

    public EmptyResponse logout(LogoutRequest logoutRequest) throws Exception {
        AuthSQLDAO aDAO = new AuthSQLDAO();
        if (ServiceUtils.isABadToken(aDAO, logoutRequest)) { throw new UnauthorizedException("Error: unauthorized"); }
        try {
            aDAO.deleteAuth(logoutRequest.authToken());
            return new EmptyResponse();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
