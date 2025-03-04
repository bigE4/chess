package service;

import dataaccess.AuthDatabaseDAO;
import exceptions.UnauthorizedException;
import request.LogoutRequest;
import response.LogoutResponse;

public class LogoutService {
    AuthDatabaseDAO aDAO = new AuthDatabaseDAO();
    public LogoutResponse logout(LogoutRequest logoutRequest) throws Exception {
        try {
            String authToken = logoutRequest.authToken();
            if (aDAO.AuthenticateAuth(authToken)) {
                aDAO.DeleteAuth(authToken);
                return new LogoutResponse();
            }
            throw new UnauthorizedException("Error: unauthorized");
        } catch (Exception e) {
            throw new Exception("Error: " + e.getMessage());
        }
    }
}
