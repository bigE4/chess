package service;

import dataaccess.AuthDatabaseDAO;
import dataaccess.UserDatabaseDAO;
import request.LoginRequest;
import response.LoginResponse;

public class LoginService {
    public LoginResponse login(LoginRequest loginRequest) {
        UserDatabaseDAO uDAO = new UserDatabaseDAO();
        AuthDatabaseDAO aDAO = new AuthDatabaseDAO();
        
    }
}
