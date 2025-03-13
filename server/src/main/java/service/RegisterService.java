package service;

import dataaccess.*;
import exceptions.BadRequestException;
import exceptions.AlreadyTakenException;
import model.AuthData;
import model.UserData;
import request.RegisterRequest;
import response.RegisterResponse;


public class RegisterService {
    public RegisterResponse register(RegisterRequest registerRequest) throws Exception {
        AuthDAO aDAO = new AuthSQLDAO();
        UserDAO uDAO = new UserSQLDAO();
        if (ServiceUtils.isABadRequest(registerRequest)) { throw new BadRequestException("Error: bad request"); }
        if (ServiceUtils.usernameTaken(uDAO, registerRequest)) { throw new AlreadyTakenException("Error: already taken"); }
        try {
            String authToken = ServiceUtils.generateToken();
            aDAO.storeAuth(new AuthData(authToken, registerRequest.username()));
            uDAO.storeUser(new UserData(registerRequest.username(), registerRequest.password(), registerRequest.email()));
            return new RegisterResponse(registerRequest.username(), authToken);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
