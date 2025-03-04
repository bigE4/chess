package service;

import dataaccess.AuthDatabaseDAO;
import exceptions.UnauthorizedException;
import request.LogoutRequest;
import response.EmptyResponse;

public class LogoutService {
    AuthDatabaseDAO aDAO = new AuthDatabaseDAO();
    public EmptyResponse logout(LogoutRequest logoutRequest) throws Exception {
        try {
            String authToken = logoutRequest.authToken();
            if (aDAO.AuthenticateAuth(authToken)) {
                aDAO.DeleteAuth(authToken);
                return new EmptyResponse();
            }
            throw new UnauthorizedException("Error: unauthorized");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
