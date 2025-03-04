package service;

import dataaccess.AuthDatabaseDAO;
import dataaccess.UserDatabaseDAO;
import exceptions.BadRequestException;
import exceptions.AlreadyTaken;
import model.AuthData;
import model.UserData;
import request.RegisterRequest;
import response.RegisterResponse;

import java.util.Objects;


public class RegisterService {
    UserDatabaseDAO uDAO = new UserDatabaseDAO();
    AuthDatabaseDAO aDAO = new AuthDatabaseDAO();
    public RegisterResponse register(RegisterRequest registerRequest) throws Exception {
        if (badRequest(registerRequest)) { throw new BadRequestException("Error: bad request"); }
        if (usernameUnavailable(registerRequest)) { throw new AlreadyTaken("Error: already taken"); }
        try {
            String username = registerRequest.username();
            String password = registerRequest.password();
            String email = registerRequest.email();
            UserData userData = new UserData(username, password, email);
            System.out.println(userData);
            uDAO.StoreUser(userData);
            String token = AuthTokenGenerator.generateToken();
            AuthData authData = new AuthData(token, username);
            aDAO.StoreAuth(authData);
            return new RegisterResponse(username, token);
        } catch (Exception e) {
            throw new Exception("Error: " + e.getMessage());
        }
    }

    public Boolean badRequest(RegisterRequest registerRequest) {
        String username = registerRequest.username();
        String password = registerRequest.password();
        String email = registerRequest.email();
        return username == null || password == null || email == null || username.isEmpty() || password.isEmpty() || email.isEmpty();
    }

    public Boolean usernameUnavailable(RegisterRequest registerRequest) {
        return uDAO.UserExists(registerRequest.username());
    }
}
