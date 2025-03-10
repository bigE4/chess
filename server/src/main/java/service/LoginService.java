package service;

import dataaccess.AuthDatabaseDAO;
import dataaccess.UserDatabaseDAO;
import exceptions.UnauthorizedException;
import model.AuthData;
import request.LoginRequest;
import response.LoginResponse;

public class LoginService {
    public LoginResponse login(LoginRequest loginRequest) throws Exception {
        AuthDatabaseDAO aDAO = new AuthDatabaseDAO();
        UserDatabaseDAO uDAO = new UserDatabaseDAO();
        if (ServiceUtils.isABadUser(uDAO, loginRequest)) { throw new UnauthorizedException("Error: unauthorized"); }
        try {
            String authToken = ServiceUtils.generateToken();
            aDAO.storeAuth(new AuthData(authToken, loginRequest.username()));
            return new LoginResponse(loginRequest.username(), authToken);
        } catch (Exception e) {
            throw new Exception("Error: " + e.getMessage());
        }
    }
}
