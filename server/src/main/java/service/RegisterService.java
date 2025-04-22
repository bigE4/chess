package service;

import dataaccess.dao.AuthSQLDAO;
import dataaccess.dao.UserSQLDAO;
import dataaccess.interfaces.AuthDAO;
import dataaccess.interfaces.UserDAO;
import exceptions.BadRequestException;
import exceptions.AlreadyTakenException;
import records.AuthData;
import records.UserData;
import records.RegisterRequest;
import records.RegisterResponse;


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
