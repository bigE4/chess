package service;

import dataaccess.AuthDatabaseDAO;
import dataaccess.UserDatabaseDAO;
import exceptions.UnauthorizedException;
import model.AuthData;
import request.LoginRequest;
import response.LoginResponse;

public class LoginService {
    UserDatabaseDAO uDAO = new UserDatabaseDAO();
    AuthDatabaseDAO aDAO = new AuthDatabaseDAO();
    public LoginResponse login(LoginRequest loginRequest) throws Exception {
        try {
            String username = loginRequest.username();
            String password = loginRequest.password();
            if (uDAO.AuthenticateUser(username, password)) {
                String token = ServiceUtils.GenerateToken();
                AuthData authData = new AuthData(token, username);
                aDAO.StoreAuth(authData);
                return new LoginResponse(username, token);
            }
            throw new UnauthorizedException("Error: unauthorized");
        } catch (UnauthorizedException unauthorizedException) {
            throw new UnauthorizedException("Error: unauthorized");
        } catch (Exception e) {
            throw new Exception("Error: " + e.getMessage());
        }
    }
}
