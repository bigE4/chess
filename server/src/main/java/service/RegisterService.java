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
    public RegisterResponse Register(RegisterRequest registerRequest) throws Exception {
        AuthDatabaseDAO aDAO = new AuthDatabaseDAO();
        UserDatabaseDAO uDAO = new UserDatabaseDAO();
        if (ServiceUtils.BadRequest(registerRequest)) { throw new BadRequestException("Error: bad request"); }
        if (ServiceUtils.UsernameTaken(uDAO, registerRequest)) { throw new AlreadyTakenException("Error: already taken"); }
        try {
            String authToken = ServiceUtils.GenerateToken();
            aDAO.StoreAuth(new AuthData(authToken, registerRequest.username()));
            uDAO.StoreUser(new UserData(registerRequest.username(), registerRequest.password(), registerRequest.email()));
            return new RegisterResponse(registerRequest.username(), authToken);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
