package service;

import dataaccess.AuthDatabaseDAO;
import dataaccess.UserDatabaseDAO;
import exceptions.BadRequestException;
import exceptions.AlreadyTakenException;
import model.AuthData;
import model.UserData;
import request.RegisterRequest;
import response.RegisterResponse;


public class RegisterService {
    UserDatabaseDAO uDAO = new UserDatabaseDAO();
    AuthDatabaseDAO aDAO = new AuthDatabaseDAO();
    public RegisterResponse register(RegisterRequest registerRequest) throws Exception {
        if (ServiceUtils.BadRequest(registerRequest)) { throw new BadRequestException("Error: bad request"); }
        if (ServiceUtils.UsernameUnavailable(uDAO, registerRequest)) { throw new AlreadyTakenException("Error: already taken"); }
        try {
            String username = registerRequest.username();
            String password = registerRequest.password();
            String email = registerRequest.email();
            UserData userData = new UserData(username, password, email);
            System.out.println(userData);
            uDAO.StoreUser(userData);
            String token = ServiceUtils.GenerateToken();
            AuthData authData = new AuthData(token, username);
            aDAO.StoreAuth(authData);
            return new RegisterResponse(username, token);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
