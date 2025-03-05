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
        if (ServiceUtils.UsernameUnavailable(uDAO, registerRequest)) { throw new AlreadyTakenException("Error: already taken"); }
        try {
            aDAO.StoreAuth(new AuthData(ServiceUtils.GenerateToken(), registerRequest.username()));
            uDAO.StoreUser(new UserData(registerRequest.username(), registerRequest.password(), registerRequest.email()));
            return new RegisterResponse(registerRequest.username(), ServiceUtils.GenerateToken());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
