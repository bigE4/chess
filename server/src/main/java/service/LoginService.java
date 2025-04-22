package service;

import dataaccess.dao.AuthSQLDAO;
import dataaccess.dao.UserSQLDAO;
import dataaccess.interfaces.AuthDAO;
import dataaccess.interfaces.UserDAO;
import exceptions.UnauthorizedException;
import records.AuthData;
import records.LoginRequest;
import records.LoginResponse;

public class LoginService {
    public LoginResponse login(LoginRequest loginRequest) throws Exception {
        AuthDAO aDAO = new AuthSQLDAO();
        UserDAO uDAO = new UserSQLDAO();
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
